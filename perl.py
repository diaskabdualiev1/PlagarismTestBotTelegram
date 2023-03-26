import os
import subprocess
from telegram import Update, InputFile
from telegram.ext import Updater, CommandHandler, MessageHandler, Filters, CallbackContext

BOT_TOKEN = "your-bot-token"  # Replace with your bot token
PERL_SCRIPT = "/path/to/perl-script.pl"  # Replace with the path to your Perl script
FILES_FOLDER = "/path/to/your/folder"  # Replace with the path to the folder where you want to save the files

def start(update: Update, context: CallbackContext):
    update.message.reply_text("Send at least two files to process.")

def handle_files(update: Update, context: CallbackContext):
    file_count = len(update.message.document)

    if file_count < 2:
        update.message.reply_text("Please send at least two files.")
        return

    file_paths = []

    for document in update.message.document:
        file = context.bot.getFile(document.file_id)
        file_name = os.path.join(FILES_FOLDER, document.file_name)
        file.download(file_name)
        file_paths.append(file_name)

    process_files(update, file_paths)

def process_files(update: Update, file_paths):
    try:
        command = ["perl", PERL_SCRIPT] + file_paths
        result = subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        if result.stderr:
            update.message.reply_text(f"Error: {result.stderr}")
        else:
            update.message.reply_text(f"Output:\n{result.stdout}")

    finally:
        for file_path in file_paths:
            os.remove(file_path)

def main():
    updater = Updater(BOT_TOKEN, use_context=True)

    dp = updater.dispatcher
    dp.add_handler(CommandHandler("start", start))
    dp.add_handler(MessageHandler(Filters.document, handle_files))

    updater.start_polling()
    updater.idle()

if __name__ == "__main__":
    main()
