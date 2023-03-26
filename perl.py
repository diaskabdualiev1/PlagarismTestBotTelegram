import os
import subprocess
import telebot
from telebot.types import Message, Document
from telebot.types import ReplyKeyboardMarkup, KeyboardButton, Message


keyboard = ReplyKeyboardMarkup(resize_keyboard=True)
button = KeyboardButton('Run script')
keyboard.add(button)


BOT_TOKEN = "5964401295:AAFknoLLBy-DOXCDmZ5dfUs9DwRuBFMH6i4"  # Replace with your bot token
PERL_SCRIPT = "moss.pl"  # Replace with the path to your Perl script
FILES_FOLDER = "storage"  # Replace with the path to the folder where you want to save the files

bot = telebot.TeleBot(BOT_TOKEN)

@bot.message_handler(content_types=['document'])
def handle_files(message: Message):
    try:
        file_paths = []
        file = message.document
        file_path = os.path.join(FILES_FOLDER, file.file_name)
        file_paths.append(file_path)
        file_info = bot.get_file(file.file_id)
        downloaded_file = bot.download_file(file_info.file_path)
        with open(file_path, 'wb') as f:
            f.write(downloaded_file)
    except Exception as e:
        bot.reply_to(message, f"Error: {str(e)}")

keyboard = ReplyKeyboardMarkup(resize_keyboard=True)
run_button = KeyboardButton('Run script')
delete_button = KeyboardButton('Delete files')
show_button = KeyboardButton('Show files')
keyboard.add(run_button, delete_button, show_button)

@bot.message_handler(commands=['start', 'help'])
def send_welcome(message: Message):
    bot.reply_to(message, "Welcome to the file processing bot!", reply_markup=keyboard)

@bot.message_handler(func=lambda message: message.text == 'Run script')
def run_script(message: Message):
    try:
        # здесь вы можете добавить свой код для обработки файла и отправки сообщения
        
        files = os.listdir(FILES_FOLDER)
        
        # запускаем Perl скрипт и передаем ему все файлы в качестве аргументов
        script_path = 'moss.pl'
        script_args = [os.path.join(FILES_FOLDER, file) for file in files]
        output = subprocess.check_output(['perl', script_path] + script_args)
        
        # отправляем результат работы скрипта пользователю
        bot.send_message(message.chat.id, output.decode('utf-8'))
    except Exception as e:
        # отправляем сообщение об ошибке
        bot.send_message(message.chat.id, f"Error running script: {e}")


@bot.message_handler(func=lambda message: message.text == 'Delete files')
def delete_files(message: Message):
    try:
        # удаляем все файлы в указанной директории
        files = os.listdir(FILES_FOLDER)
        for file in files:
            os.remove(os.path.join(FILES_FOLDER, file))
        
        # отправляем сообщение об успешном удалении файлов
        bot.send_message(message.chat.id, "All files have been deleted!")
    except Exception as e:
        # отправляем сообщение об ошибке
        bot.send_message(message.chat.id, f"Error deleting files: {e}")

@bot.message_handler(func=lambda message: message.text == 'Show files')
def show_files(message: Message):
    try:
        # получаем список файлов в указанной директории
        files = os.listdir(FILES_FOLDER)
        
        # формируем сообщение с перечислением файлов
        files_list = "\n".join(files)
        message_text = f"Files in {FILES_FOLDER}:\n{files_list}"
        
        # отправляем сообщение пользователю
        bot.send_message(message.chat.id, message_text)
    except Exception as e:
        # отправляем сообщение об ошибке
        bot.send_message(message.chat.id, f"Error showing files: {e}")

bot.polling()