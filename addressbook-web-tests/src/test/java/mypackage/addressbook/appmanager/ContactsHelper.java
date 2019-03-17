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

    public void createContact(ContactData contact) {
        fillContactCreationForm(contact);
        submitContactCreationForm();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int countContacts() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> row = new ArrayList<>(wd.findElements(By.xpath("//tr[@name = 'entry']")));
        List<WebElement> cell = new ArrayList<>(wd.findElements(By.xpath("//tr[@name = 'entry']/td")));
        for(int i = 0; i < row.size(); i++) {
                String lastName = cell.get(1).getText();
                String firstName = cell.get(2).getText();
                String id = row.get(i).findElement(By.tagName("input")).getAttribute("value");
                ContactData contact = new ContactData(id, firstName, lastName,
                        null, null,
                        null, null,
                        null, null, null,null);
                contacts.add(contact);
            }
        return contacts;
    }
}