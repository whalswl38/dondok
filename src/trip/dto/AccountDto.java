package trip.dto;

public class AccountDto {
int acc_tid;
String acc_holder;
String acc_bank;
String acc_num;
String acc_price;
String acc_mileage;

public AccountDto() {
	super();
}
public AccountDto(int acc_tid, String acc_holder, String acc_bank, String acc_num) {
	super();
	this.acc_tid = acc_tid;
	this.acc_holder = acc_holder;
	this.acc_bank = acc_bank;
	this.acc_num = acc_num;
}
public AccountDto(int acc_tid, String acc_holder, String acc_bank, String acc_num, String acc_price) {
	super();
	this.acc_tid = acc_tid;
	this.acc_holder = acc_holder;
	this.acc_bank = acc_bank;
	this.acc_num = acc_num;
	this.acc_price = acc_price;
}
public AccountDto(int acc_tid, String acc_holder, String acc_bank, String acc_num, String acc_price,
		String acc_mileage) {
	super();
	this.acc_tid = acc_tid;
	this.acc_holder = acc_holder;
	this.acc_bank = acc_bank;
	this.acc_num = acc_num;
	this.acc_price = acc_price;
	this.acc_mileage = acc_mileage;
}
public int getAcc_tid() {
	return acc_tid;
}
public void setAcc_tid(int acc_tid) {
	this.acc_tid = acc_tid;
}
public String getAcc_holder() {
	return acc_holder;
}
public void setAcc_holder(String acc_holder) {
	this.acc_holder = acc_holder;
}
public String getAcc_bank() {
	return acc_bank;
}
public void setAcc_bank(String acc_bank) {
	this.acc_bank = acc_bank;
}
public String getAcc_num() {
	return acc_num;
}
public void setAcc_num(String acc_num) {
	this.acc_num = acc_num;
}
public String getAcc_price() {
	return acc_price;
}
public void setAcc_price(String acc_price) {
	this.acc_price = acc_price;
}
public String getAcc_mileage() {
	return acc_mileage;
}
public void setAcc_mileage(String acc_mileage) {
	this.acc_mileage = acc_mileage;
}

}
