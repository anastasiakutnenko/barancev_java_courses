package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddNewContactTests extends TestBase {

    @Test
    public void addNewContactTest() throws Exception {
        int before = app.getContactsHelper().countContacts();
        app.getNavigationHelper().goToContactCreationPage();
        app.getContactsHelper().createContact(new ContactData(
                "Anastasia", "Kutnenko",
                "Shevchenka 100", "111-111-111",
                "0923456789", "222-222-222",
                "333-333-333", "anastasya.kutnenko+1@gmail.com",
                "anastasya.kutnenko+2@gmail.com", "anastasya.kutnenko+3@gmail.com"));
        app.getNavigationHelper().goToHomePage();
        int after = app.getContactsHelper().countContacts();
        Assert.assertEquals(after, before + 1);
    }
}
