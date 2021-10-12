package com.tsg.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.springframework.stereotype.Component;
@Component
public class UserIOConsoleImpl implements UserIO{
	private final Scanner scan = new Scanner(System.in);
	
	@Override
	public void print(String msg) {
		System.out.println(msg);
	}

	@Override
	public double readDouble(String prompt) {
		 while (true) {
	            try {
	                return Double.parseDouble(this.readString(prompt));
	            } catch (NumberFormatException e) {
	                this.print("Input error. Please try again.");
	            }
	        }
	}

	@Override
	public double readDouble(String prompt, double min, double max) {
		double result;
        do {
            result = readDouble(prompt);
        } while (result < min || result > max);
        return result;
	}

	@Override
	public float readFloat(String prompt) {
		while (true) {
			try {
	            return Float.parseFloat(this.readString(prompt));
	        } catch (NumberFormatException e) {
	            this.print("Input error. Please try again.");
	        }
	    }
	}

	@Override
	public float readFloat(String prompt, float min, float max) {
		float result;
        do {
            result = readFloat(prompt);
        } while (result < min || result > max);

        return result;
	}

	@Override
	public int readInt(String prompt) {
		boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Integer.parseInt(stringValue); 
                invalidInput = false; 
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
	}

	@Override
	public int readInt(String prompt, int min, int max) {
		  int result;
	        do {
	            result = readInt(prompt);
	        } while (result < min || result > max);

	        return result;
	}

	@Override
	public long readLong(String prompt) {
		while (true) {
            try {
                return Long.parseLong(this.readString(prompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
	}

	@Override
	public long readLong(String prompt, long min, long max) {
		long result;
        do {
            result = readLong(prompt);
        } while (result < min || result > max);

        return result;
    }
	

	@Override
	public String readString(String prompt) {
		System.out.println(prompt);
	    return scan.nextLine();
	}

	@Override
	public void print(int num) {
		System.out.println(num);
		
	}

	@Override
	public void print(BigDecimal money) {
		System.out.println(money);
	}
	
	@Override
	public BigDecimal readBigDecimal(String prompt) {
		while (true) {
            try {
                return new BigDecimal(this.readString(prompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
	}
	
	@Override
	public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
		BigDecimal result;
		do {
			result = readBigDecimal(prompt);
		}while (result.compareTo(min) < 0);
		return result;
	}

	@Override
	public LocalDate readLocalDate(String prompt) {
		while (true) {
			try {
				return LocalDate.parse(this.readString(prompt));
			}catch (DateTimeParseException e) {
				this.print("Input error. Please try again.");
			}
		}
	}

	@Override
	public LocalDate readLocalDate(String prompt, LocalDate min) {
		LocalDate result;
		while(true) {
			do {
				result = readLocalDate(prompt);
			}while (result.isBefore(min));
			return result;
		}
	}

	@Override
	public char readChar(String prompt) {
		System.out.println(prompt);
		return scan.nextLine().charAt(0);
	}

		

}
