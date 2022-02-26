
package Telegram;

import JavaSheetBotAllTest.App;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BotBuilder {
     
    private final  App bot;
    
    public BotBuilder(App bot){  
        this.bot = bot;
    }
    
    public void sendMsg(String chatId, String text){
        try {
            bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build());
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void sendMsg(String chatId, String text, List button){
        try {
            bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(button).build())
                .build());
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void sendMsg(String chatId, String text, List button, boolean oneTime){
        try {
            bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(ReplyKeyboardMarkup.builder()
                .oneTimeKeyboard(oneTime)
                .resizeKeyboard(true)
                .keyboard(button)
                .build()).build());
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void editMsg(int messId, String chatId, List button){
        try {
            bot.execute(EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messId)
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(button).build())
                .build());
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public List getInlineButton(String[][] text){
        List<List<InlineKeyboardButton>> button = new ArrayList<>();
        for (String[] text1 : text) {
            List<InlineKeyboardButton> but = new ArrayList<>();
            for (String text11 : text1) {
                but.add(InlineKeyboardButton.builder().text(text11).callbackData(text11).build());
            }
            button.add(but);
        }
        return button;
    }    
    
    public List getKeyboardButton(String[][] text){
        List<KeyboardRow> button = new ArrayList<>();
        for (String[] text1 : text) {
            KeyboardRow row = new KeyboardRow();
            for (String text11 : text1) {
                row.add(KeyboardButton.builder().text(text11).build());
            }
            button.add(row);
        }
        return button;
    }   
    
    
   
    @SuppressWarnings("unchecked")
    public void editMessage(String chatId, int mesId, String text, List button){
        try {
            bot.execute(EditMessageText.builder()
                .chatId(chatId)
                .messageId(mesId)
                .text(text)
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(button).build())
                .build());
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editMessage(String chatId, int mesId, String text){
        try {
            bot.execute(EditMessageText.builder()
                .chatId(chatId)
                .messageId(mesId)
                .text(text)
                .build());
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void asnver(String text, String cqId, boolean alert){
        try {
            bot.execute(AnswerCallbackQuery.builder().text(text).callbackQueryId(cqId).showAlert(alert).build());
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
