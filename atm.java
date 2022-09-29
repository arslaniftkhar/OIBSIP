package com.company;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

class bank
{
    double balance;
    String name;
    int accno;
    String history;
    String main;
    String password;
    bank()
    {
        try
        {
            File obj = new File("C:\\Users\\arslan\\Documents\\accountcount.txt");
            Scanner reader = new Scanner(obj);
            String data = "";
            if(reader.hasNextLine())
            {
                data = reader.nextLine();
            }
            accno = Integer.parseInt(data);
            accno++;
            reader.close();
            FileWriter objw = new FileWriter("C:\\Users\\arslan\\Documents\\accountcount.txt");
            objw.write(Integer.toString(accno));
            objw.close();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter account holder's name = ");
            name = sc.nextLine();
            System.out.println("Enter initial balance = ");
            balance = sc.nextDouble();
            String pword;
            System.out.println("Set account password = ");
            pword = sc.nextLine();
            pword = sc.nextLine();
            password = pword;
            try {
                String newacc = Integer.toString(accno);
                history = "C:\\Users\\arslan\\Documents\\" + newacc + "history.txt";
                File newaccrecord = new File(history);
                main = "C:\\Users\\arslan\\Documents\\" + newacc + ".txt";
                File myObj = new File(main);
                if (myObj.createNewFile() && newaccrecord.createNewFile()) {
                    System.out.println("Account created: " + myObj.getName());
                } else {
                    System.out.println("Account already exists.");
                }
                menu();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    bank(int x)
    {
        accno = x;
        Scanner sc = new Scanner(System.in);
        String accpath = Integer.toString(x);
        try
        {
            accpath = "C:\\Users\\arslan\\Documents\\" + accpath + ".txt";
            String accHistoryPath = "C:\\Users\\arslan\\Documents\\" + Integer.toString(accno) + "history.txt";
            File obj = new File(accpath);
            history = accHistoryPath;
            main = accpath;
            Scanner reader = new Scanner(obj);
            String data="";
            if(reader.hasNextLine())
                data = reader.nextLine();
            String[] datasplit = data.split(" ");
            System.out.println("Enter account password = ");
            String pword = sc.nextLine();
            boolean flag = false;
            if(Objects.equals(pword, datasplit[0]))
            {
                System.out.println("Login Successful.");
                flag = true;
            }
            else
            {
                System.out.println(datasplit[0]);
                System.out.println("Login failed.");
            }
            if(flag) {
                password = datasplit[0];
                name = datasplit[1];
                balance = Double.parseDouble(datasplit[2]);
                menu();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    bank(int x, int y)
    {
        accno = x;
        Scanner sc = new Scanner(System.in);
        String accpath = Integer.toString(x);
        try
        {
            accpath = "C:\\Users\\arslan\\Documents\\" + accpath + ".txt";
            String accHistoryPath = "C:\\Users\\arslan\\Documents\\" + accpath + "history.txt";
            File obj = new File(accpath);
            history = accHistoryPath;
            main = accpath;
            Scanner reader = new Scanner(obj);
            String data="";
            if(reader.hasNextLine())
                data = reader.nextLine();
            String[] datasplit = data.split(" ");
            password = datasplit[0];
            name = datasplit[1];
            balance = Double.parseDouble(datasplit[2]);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
        }
    }
    void readHistory()
    {
        System.out.println("Account statement: ");
        try{
            File obj = new File(history);
            Scanner reader = new Scanner(obj);
            System.out.println("Balance: " + balance + "\n");
            while(reader.hasNextLine())
            {
                System.out.println(reader.nextLine());
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }
    void withdraw(double withdrawAmount) throws IOException {
        FileWriter obj = new FileWriter(history, true);
        if(balance - withdrawAmount >=0)
        {
            balance -= withdrawAmount;
            System.out.println("Transaction successful.");
            obj.append("Amount withdrawn: ").append(Double.toString(withdrawAmount)).append("\n");
            System.out.println("Balance: " + balance + "\n");
            obj.close();
        }
        else
        {
            System.out.println("Transaction failed. Insufficient balance.");
        }
    }
    void deposit(double depositAmount) {
        try
        {
            FileWriter obj = new FileWriter(history, true);
            if(depositAmount > 0)
            {
                balance += depositAmount;
                obj.append("Amount deposited: ").append(Double.toString(depositAmount)).append("\n");
                System.out.println("Balance: " + balance + "\n");
                obj.close();
            }
        }
        catch(IOException e)
        {
            System.out.println("IOException in deposit function, history = " + history);
        }
    }
    void menu() throws IOException {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("1.Withdraw\n2.Deposit\n3.Transfer balance\n4.Transaction History\n5.Exit\n");
        choice = sc.nextInt();
        switch(choice)
        {
            case 1:
                System.out.println("Enter amount to withdraw = ");
                double withdrawAmount = sc.nextDouble();
                withdraw(withdrawAmount);
                break;
            case 2:
                System.out.println("Enter deposit amount = ");
                double depositAmount = sc.nextDouble();
                deposit(depositAmount);
                break;
            case 3:
                int accno2;
                System.out.println("Enter account number to transfer balance with = ");
                accno2 = sc.nextInt();
                bank ob2 = new bank(accno2,1);
                System.out.println("Enter amount of money to transfer = ");
                double withdrawAmt = sc.nextDouble();
                if(withdrawAmt > balance) {
                    System.out.println("Insufficient balance, try again. " + balance);
                    break;
                }
                withdraw(withdrawAmt);
                ob2.deposit(withdrawAmt);
                ob2.storeData();
                break;
            case 4:
                readHistory();
                break;
            case 5:
                break;
            default:
                System.out.println("Wrong choice, try again.");
        }
        storeData();
    }
    void storeData() throws IOException {
        System.out.println("Storing data in " + main);
        FileWriter obj = new FileWriter(main);
        obj.write(password + " " + name + " " + Double.toString(balance));
        obj.close();
    }
    void readTransactionHistory(int accno)
    {
        try{
            String accnum = "C:\\Users\\arslan\\Documents\\" + Integer.toString(accno) + ".txt";
            File obj = new File(accnum);
            Scanner reader = new Scanner(obj);
            String data;
            if(reader.hasNextLine())
            {
                data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice1;
        System.out.println("1. Create account\n2. Login to account\n");
        choice1 = sc.nextInt();
        switch(choice1)
        {
            case 1:
                bank ob = new bank();
                break;
            case 2:
                int temp;
                System.out.println("Enter account number: ");
                temp = sc.nextInt();
                bank ob1 = new bank(temp);
                break;
            default:
                System.out.println("Wrong choice, try again.");
        }
    }
}
