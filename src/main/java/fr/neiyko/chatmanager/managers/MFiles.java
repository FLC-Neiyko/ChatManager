package fr.neiyko.chatmanager.managers;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class MFiles {

    private ChatManager chatManager = ChatManager.getInstance();

    public void initFile() {

        ressourceSetup("config.yml", true);
        chatManager.fileConfigConfiguration = YamlConfiguration.loadConfiguration(chatManager.configFile);

        ressourceSetup("messages.yml", true);
        chatManager.fileConfigMessages = YamlConfiguration.loadConfiguration(chatManager.messagesFile);

        ressourceSetup("data.yml", true);
        chatManager.fileConfigData = YamlConfiguration.loadConfiguration(chatManager.dataFile);

        ressourceSetup("blockedwords.yml", true);
        chatManager.fileConfigBlockedWords = YamlConfiguration.loadConfiguration(chatManager.wordsFile);

        ressourceSetup("blockedlinks.yml", true);
        chatManager.fileConfigLinks = YamlConfiguration.loadConfiguration(chatManager.linksFile);

    }

    public void ressourceSetup(String fileName, boolean reset) {
        InputStream in = chatManager.getResource((fileName));

        if (in == null) {
            throw new IllegalArgumentException("The '" + fileName + "' resource was not found !");
        }

        File outDir = new File(chatManager.getDataFolder(), "");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        String fileNameString = fileName.toLowerCase();
        if (fileNameString.equals("config.yml") || fileName.equals("messages.yml") || fileName.equals("data.yml") || fileName.equals("blockedwords.yml")
        || fileName.equals("blockedlinks.yml")) {
            File outFile = new File(chatManager.getDataFolder(), fileName);
            if (!outFile.exists() || reset) {
                try {
                    OutputStream out = new FileOutputStream(outFile);
                    byte[] buf = new byte['?'];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.close();
                    in.close();
                    return;
                } catch (Exception e) {
                    chatManager.logConsole(Level.WARNING, "The '" + fileName + "' was not found !");
                }
            }
        }
    }

    public void saveResource(File fileName) {
        if(fileName == chatManager.dataFile) {
            try {
                chatManager.fileConfigData.save(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            chatManager.getLogger().log(Level.WARNING, "The file '" + fileName.getName() + "' cannot be saved !");
        }

    }


}
