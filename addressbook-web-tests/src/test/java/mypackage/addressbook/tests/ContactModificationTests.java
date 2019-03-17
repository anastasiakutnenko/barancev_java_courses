package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.getNavigationHelper().goToHomePage();
        if (!app.getContactsHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactCreationPage();
            app.getContactsHelper().createContact(new ContactData(
                    "Anastasia", "Kutnenko",
                    "Shevchenka 100", "111-111-111",
                    "0923456789", "222-222-222",
                    "333-333-333", "anastasya.kutnenko+1@gmail.com",
                    "anastasya.kutnenko+2@gmail.com", "anastasya.kutnenko+3@gmail.com"));
        }
        List<ContactData> before = app.getContactsHelper().getContactList();
        app.getContactsHelper().selectContact(before.size() - 1);
        app.getContactsHelper().clickEditIcon();
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(),
                "Anastasia", "Kutnenko",
                "Shevchenka 100", "111-111-111",
                "0923456789", "222-222-222",
                "333-333-333", "anastasya.kutnenko+1@gmail.com",
                "anastasya.kutnenko+2@gmail.com", "anastasya.kutnenko+3@gmail.com");

        app.getContactsHelper().fillContactCreationForm(contact);
        app.getContactsHelper().clickUpdateContactButton();
        app.getContactsHelper().returnToHomePage();
        List<ContactData> after = app.getContactsHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object>(before));
    }
}
