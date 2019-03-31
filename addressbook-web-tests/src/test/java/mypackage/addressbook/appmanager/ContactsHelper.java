package mypackage.addressbook.appmanager;

import mypackage.addressbook.model.ContactData;
import mypackage.addressbook.model.Contacts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ContactsHelper extends HelperBase {

    public ContactsHelper(WebDriver wd) {
        super(wd);
    }

    public NavigationHelper goTo = new NavigationHelper(wd);

    public void submitContactCreationForm() {
        click(By.xpath("//input[@name='submit']"));
    }

    public void fillContactCreationForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        attach(By.name("photo"), contactData.getPhoto());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("fax"), contactData.getFax());
        type(By.name("email"), contactData.getEmail1());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value ='" + id + "']")).click();
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void initContactModification(int id) {
        wd.findElement(By.xpath("//a[@href = 'edit.php?id=" + id + "']")).click();
    }

    public void clickUpdateContactButton() {
        click(By.xpath("(//input[@name='update'])"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void create(ContactData contact) {
        goTo.contactCreationPage();
        fillContactCreationForm(contact);
        submitContactCreationForm();
        contactCache = null;
        goTo.homePage();

    }

    public void modify(ContactData contact) {
        initContactModification(contact.getId());
        fillContactCreationForm(contact);
        clickUpdateContactButton();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        goTo.acceptBrowserAlert();
        contactCache = null;
        goTo.homePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> rows = new ArrayList<>(wd.findElements(By.xpath("//tr[@name = 'entry']")));
        for(int i = 1; i <= rows.size(); i++) {
            WebElement row = wd.findElement(By.xpath("//tr[@name = 'entry']["+ i +"]"));
            List<WebElement> cell = new ArrayList<>(row.findElements(By.xpath("./td")));
            String lastName = cell.get(1).getText();
            String firstName = cell.get(2).getText();
            String address = cell.get(3).getText();
            String allEmails = cell.get(4).getText();
            String allPhones = cell.get(5).getText();
            int id = Integer.parseInt(cell.get(0).findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData()
                    .withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withAddress(address)
                    .withAllEmails(allEmails)
                    .withAllPhones(allPhones);
            contactCache.add(contact);
        }
        return contactCache;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModification(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String email1 = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstName)
                .withLastName(lastName)
                .withHomePhone(homePhone)
                .withMobilePhone(mobilePhone)
                .withWorkPhone(workPhone)
                .withEmail1(email1)
                .withEmail2(email2)
                .withEmail3(email3);
    }
}