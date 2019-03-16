package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import org.testng.annotations.Test;

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
        app.getContactsHelper().selectContact();
        app.getContactsHelper().clickEditIcon();
        app.getContactsHelper().fillContactCreationForm(new ContactData(
                "Anastasia updated", "Kutnenko updated",
                "Shevchenka 100 updated", "111-111-111 updated",
                "0923456789 updated", "222-222-222 updated",
                "333-333-333 updated", "anastasya.kutnenko+1@gmail.com updated",
                "anastasya.kutnenko+2@gmail.com updated", "anastasya.kutnenko+3@gmail.com updated"));
        app.getContactsHelper().clickUpdateContactButton();
        app.getContactsHelper().returnToHomePage();
    }
}
