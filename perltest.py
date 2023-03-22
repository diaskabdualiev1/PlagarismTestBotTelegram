import os
import subprocess
from telegram.ext import Updater, CommandHandler, MessageHandler, Filters

TOKEN = '5964401295:AAFknoLLBy-DOXCDmZ5dfUs9DwRuBFMH6i4'
SAVE_DIR = '/storage'

def start(update, context):
    update.message.reply_text('Привет! Отправьте два или более файла.')

def save_files(update, context):
    files = update.message.document
    if len(files) < 2:
        update.message.reply_text('Отправьте не менее двух файлов.')
        return

    # Сохраняем файлы в указанную папку
    saved_files = []
    for file in files:
        file_path = os.path.join(SAVE_DIR, file.file_name)
        file.get_file().download(file_path)
        saved_files.append(file_path)

    # Запускаем Perl скрипт, передав ему сохраненные файлы в качестве аргументов
    perl_script_path = '/storage/moss.pl'
    perl_command = ['perl', perl_script_path] + saved_files
    process = subprocess.Popen(perl_command, stdout=subprocess.PIPE)

    # Получаем вывод скрипта и отправляем его пользователю
    output = process.communicate()[0].decode('utf-8')
    update.message.reply_text(output)

    # Удаляем сохраненные файлы
    for file_path in saved_files:
        os.remove(file_path)

def main():
    updater = Updater(TOKEN, use_context=True)
    dp = updater.dispatcher
    dp.add_handler(CommandHandler("start", start))
    dp.add_handler(MessageHandler(Filters.document, save_files))

    updater.start_polling()
    updater.idle()

if __name__ == '__main__':
    main()
