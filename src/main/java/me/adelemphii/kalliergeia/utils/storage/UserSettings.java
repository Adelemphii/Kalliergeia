package me.adelemphii.kalliergeia.utils.storage;

public class UserSettings {

    private String UUID;

    private boolean isCropTrample;
    private boolean autoReplant;

    public UserSettings(String UUID, boolean isCropTrample, boolean autoReplant) {
        this.UUID = UUID;
        this.isCropTrample = isCropTrample;
        this.autoReplant = autoReplant;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }

    public boolean isCropTrample() {
        return isCropTrample;
    }

    public void setCropTrample(boolean cropTrample) {
        this.isCropTrample = cropTrample;
    }

    public boolean isAutoReplant() {
        return autoReplant;
    }

    public void setAutoReplant(boolean autoReplant) {
        this.autoReplant = autoReplant;
    }
}
