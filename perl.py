import telebot
import subprocess
import os

# Replace YOUR_TELEGRAM_BOT_TOKEN with your own bot token from BotFather
bot = telebot.TeleBot('5964401295:AAFknoLLBy-DOXCDmZ5dfUs9DwRuBFMH6i4')

# Replace YOUR_DIRECTORY_PATH with the directory path where you want to save the files
directory_path = '/storage'

@bot.message_handler(content_types=['document'])
def handle_docs(message):
    try:
        # Download the file
        file_info = bot.get_file(message.document.file_id)
        downloaded_file = bot.download_file(file_info.file_path)

        # Save the file to the specified directory
        file_name = message.document.file_name
        file_path = os.path.join(directory_path, file_name)
        with open(file_path, 'wb') as f:
            f.write(downloaded_file)

        # Run the Perl script with the file path as argument
        perl_script_path = '/storage/moss.pl'
        perl_output = subprocess.check_output(['perl', perl_script_path, file_path])

        # Send the output of the Perl script to the user
        bot.send_message(message.chat.id, perl_output.decode('utf-8'))

    except Exception as e:
        bot.reply_to(message, e)

# Start the bot
bot.polling()
