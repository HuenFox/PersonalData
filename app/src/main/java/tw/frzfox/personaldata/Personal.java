package tw.frzfox.personaldata;

public class Personal {
    private String Account;
    private String Password;
    private String Zodiac;
    private String BloodType;
    private String CreateTime;
    private String UpdateTime;

    Personal(String Account, String Password, String Zodiac, String BloodType, String CreateTime, String UpdateTime) {
        this.Account = Account;
        this.Password = Password;
        this.Zodiac = Zodiac;
        this.BloodType = BloodType;
        this.CreateTime = CreateTime;
        this.UpdateTime = UpdateTime;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getZodiac() {
        return Zodiac;
    }

    public void setZodiac(String zodiac) {
        Zodiac = zodiac;
    }

    public String getBloodType() {
        return BloodType;
    }

    public void setBloodType(String bloodType) {
        BloodType = bloodType;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
