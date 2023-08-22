package rikkei.academy.config;

public class Validate {
	
	public static boolean checkPhone(String phone) {
		String regexPhone = "/(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\\b/";
		if (phone.length() != 11) {
			return false;
		}
		if (!phone.matches(regexPhone)) {
			return false;
		}
		return true;
	}
	
	public static boolean checkPassword(String password) {
		if (password.length() < 6) {
			return false;
		}
		String[] str = password.split("");
		for (String s : str) {
			if (s.equals(" ")) {
				return false;
			}
		}
		return true;
	}
}
