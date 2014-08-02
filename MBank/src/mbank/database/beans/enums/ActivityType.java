/**
 * 
 */
package mbank.database.beans.enums;

/**
 * @author Shlomit Argov
 * 
 */
public enum ActivityType {

	ADD_NEW_CLIENT(0), UPDATE_CLIENT_DETAILS(1), REMOVE_CLIENT(2), CREATE_NEW_ACCOUNT(
			3), REMOVE_ACCOUNT(4), WITHDRAW_FROM_ACCOUNT(5), DEPOSIT_TO_ACCOUNT(
			6), CREATE_NEW_DEPOSIT(7), CLOSE_DEPOSIT(8), PRE_OPEN_DEPOSIT(9), VIEW_CLIENT_DETAILS(
			10), VIEW_ALL_CLIENT_DETAILS(11), VIEW_ACCOUNT_DETAILS(12), VIEW_ALL_ACCOUNT_DETAILS(
			13), VIEW_CLIENT_DEPOSITS(14), VIEW_ALL_CLIENT_DEPOSITS(15), VIEW_CLIENT_ACTIVITIES(
			16), VIEW_ALL_ACTIVITIES(17), UPDATE_SYSTEM_PROPERTY(18), VIEW_SYSTEM_PROPERTY(
			19), REMOVE_DEPOSIT(20);

	private int val;

	private ActivityType(int val) {
		this.val = val;
	}

	public int getVal() {
		// TODO Auto-generated method stub
		return val;
	}

	public static ActivityType intToType(int i) {
		switch (i) {
		case (0):
			return ADD_NEW_CLIENT;
		case (1):
			return UPDATE_CLIENT_DETAILS;
		case (2):
			return REMOVE_CLIENT;
		case (3):
			return CREATE_NEW_ACCOUNT;
		case (4):
			return REMOVE_ACCOUNT;
		case (5):
			return WITHDRAW_FROM_ACCOUNT;
		case (6):
			return DEPOSIT_TO_ACCOUNT;
		case (7):
			return CREATE_NEW_DEPOSIT;
		case (8):
			return CLOSE_DEPOSIT;
		case (9):
			return PRE_OPEN_DEPOSIT;
		case (10):
			return ActivityType.VIEW_CLIENT_DETAILS;
		case (11):
			return VIEW_ALL_CLIENT_DETAILS;
		case (12):
			return VIEW_ACCOUNT_DETAILS;
		case (13):
			return VIEW_ALL_ACCOUNT_DETAILS;
		case (14):
			return VIEW_CLIENT_DEPOSITS;
		case (15):
			return VIEW_ALL_CLIENT_DEPOSITS;
		case (16):
			return VIEW_CLIENT_ACTIVITIES;
		case (17):
			return VIEW_ALL_ACTIVITIES;
		case (18):
			return UPDATE_SYSTEM_PROPERTY;
		case (19):
			return VIEW_SYSTEM_PROPERTY;
		}
		return null;

	}
}