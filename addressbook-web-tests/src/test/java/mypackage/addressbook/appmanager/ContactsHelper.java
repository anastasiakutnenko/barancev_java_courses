package mypackage.addressbook.appmanager;

import mypackage.addressbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ContactsHelper extends HelperBase {

    public ContactsHelper(WebDriver wd) {
        super(wd);
    }

    public NavigationHelper getNavigationHelper = new NavigationHelper(wd);

    public void submitContactCreationForm() {
        click(By.xpath("//input[@name='submit']"));
    }

    public void fillContactCreationForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("fax"), contactData.getFax());
        type(By.name("email"), contactData.getEmail1());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void clickEditIcon() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void clickUpdateContactButton() {
        click(By.xpath("(//input[@name='update'])"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void create(ContactData contact) {
        fillContactCreationForm(contact);
        submitContactCreationForm();
    }

    public void modify(ContactData contact, int index) {
        selectContact(index);
        clickEditIcon();
        fillContactCreationForm(contact);
        clickUpdateContactButton();
        returnToHomePage();
    }

    public void delete(int index) {
        selectContact(index);
        deleteContact();
        getNavigationHelper.acceptBrowserAlert();
        getNavigationHelper.homePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int countContacts() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> row = new ArrayList<>(wd.findElements(By.xpath("//tr[@name = 'entry']")));
        List<WebElement> cell = new ArrayList<>(wd.findElements(By.xpath("//tr[@name = 'entry']/td")));
        for(int i = 0; i < row.size(); i++) {
                String lastName = cell.get(1).getText();
                String firstName = cell.get(2).getText();
                String address = cell.get(3).getText();
                String[] emails = cell.get(4).getText().split("\n");
                String[] phones = cell.get(5).getText().split("\n");
                int id = Integer.parseInt(row.get(i).findElement(By.tagName("input")).getAttribute("value"));
                ContactData contact = new ContactData()
                        .withId(id)
                        .withFirstName(firstName)
                        .withLastName(lastName);

                ContactData contactFullInfo = new ContactData()
                        .withId(id)
                        .withFirstName(firstName)
                        .withLastName(lastName)
                        .withAddress(address)
                        .withHomePhone(phones[0])
                        .withMobilePhone(phones[1])
                        .withWorkPhone(phones[2])
                        .withEmail1(emails[0])
                        .withEmail2(emails[1])
                        .withEmail3(emails[2]);
                contacts.add(contact);
            }
        return contacts;
    }
}