
package com.trip.dto.member;

import com.trip.util.Sha256Util;

public class MemberLoginDto {
	private String m_id;
	private String m_nick;
	private String m_pass;
	private String m_name;
	private String m_email;
	private String m_phone;
	private String m_flag;
	private String m_grade;
	private String m_platform;
	private String m_filepath;
	private String m_addr1;
	private String m_addr2;
	
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_nick() {
		return m_nick;
	}
	public void setM_nick(String m_nick) {
		this.m_nick = m_nick;
	}
	public String getM_pass() {
		return m_pass;
	}
	public void setM_pass(String m_pass) {
		//sha256으로 hash 암호화 후 저장
		String rtn = new String();
		if(m_pass.length() > 20){
			rtn = m_pass;
		}else{
			rtn = Sha256Util.encSha256(m_pass);
		}
		this.m_pass = rtn;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_email() {
		return m_email;
	}
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	public String getM_phone() {
		return m_phone;
	}
	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}
	public String getM_flag() {
		return m_flag;
	}
	public void setM_flag(String m_flag) {
		this.m_flag = m_flag;
	}
	public String getM_grade() {
		return m_grade;
	}
	public void setM_grade(String m_grade) {
		this.m_grade = m_grade;
	}
	public String getM_platform() {
		return m_platform;
	}
	public void setM_platform(String m_platform) {
		this.m_platform = m_platform;
	}
	public String getM_filepath() {
		return m_filepath;
	}
	public void setM_filepath(String m_filepath) {
		this.m_filepath = m_filepath;
	}
	public String getM_addr1() {
		return m_addr1;
	}
	public void setM_addr1(String m_addr1) {
		this.m_addr1 = m_addr1;
	}
	public String getM_addr2() {
		return m_addr2;
	}
	public void setM_addr2(String m_addr2) {
		this.m_addr2 = m_addr2;
	}
	@Override
	public String toString() {
		return "MemberLoginDto [m_id=" + m_id + ", m_nick=" + m_nick + ", m_pass=" + m_pass + ", m_name=" + m_name
				+ ", m_email=" + m_email + ", m_phone=" + m_phone + ", m_flag=" + m_flag + ", m_grade=" + m_grade
				+ ", m_platform=" + m_platform + ", m_filepath=" + m_filepath + ", m_addr1=" + m_addr1 + ", m_addr2="
				+ m_addr2 + "]";
	}
	
}

