package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstName("Anastasia").withLastName("Kutnenko")
                    .withAddress("Shevchenka 100").withHomePhone("111-111-111")
                    .withMobilePhone("0923456789").withWorkPhone("222-222-222")
                    .withFax("333-333-333").withEmail1("anastasya.kutnenko+1@gmail.com")
                    .withEmail2("anastasya.kutnenko+2@gmail.com")
                    .withEmail3("anastasya.kutnenko+3@gmail.com"));
        }
    }

    @Test
    public void testContactModification() {
        app.goTo().homePage();
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstName("Anastasia").withLastName("Kutnenko")
                .withAddress("Shevchenka 100").withHomePhone("111-111-111")
                .withMobilePhone("0923456789").withWorkPhone("222-222-222")
                .withFax("333-333-333").withEmail1("anastasya.kutnenko+1@gmail.com")
                .withEmail2("anastasya.kutnenko+2@gmail.com")
                .withEmail3("anastasya.kutnenko+3@gmail.com");
        app.contact().modify(contact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());
        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(after, before);
    }
}
