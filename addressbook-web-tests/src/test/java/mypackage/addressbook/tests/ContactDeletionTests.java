package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().goToHomePage();
        int before = app.getContactsHelper().countContacts();
        if (!app.getContactsHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactCreationPage();
            app.getContactsHelper().createContact(new ContactData(
                    "Anastasia", "Kutnenko",
                    "Shevchenka 100", "111-111-111",
                    "0923456789", "222-222-222",
                    "333-333-333", "anastasya.kutnenko+1@gmail.com",
                    "anastasya.kutnenko+2@gmail.com", "anastasya.kutnenko+3@gmail.com"));
        }
        app.getContactsHelper().selectContact(before - 1);
        app.getContactsHelper().deleteContact();
        app.getNavigationHelper().acceptBrowserAlert();
        app.getNavigationHelper().goToHomePage();
        int after = app.getContactsHelper().countContacts();
        Assert.assertEquals(after, before - 1);
    }
}
