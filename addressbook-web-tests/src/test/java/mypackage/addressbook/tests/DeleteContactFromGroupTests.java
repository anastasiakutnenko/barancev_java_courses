package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import mypackage.addressbook.model.Contacts;
import mypackage.addressbook.model.GroupData;
import mypackage.addressbook.model.Groups;
import org.hamcrest.Matchers;
import org.hibernate.Session;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class DeleteContactFromGroupTests extends TestBase{
    File photo = new File("src/test/resources/avatar.png");
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData()
                    .withName("test1").withHeader("test1").withFooter("test1"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstName("Anastasia").withLastName("Kutnenko")
                    .withAddress("Shevchenka 100").withHomePhone("111-111-111")
                    .withMobilePhone("0923456789").withWorkPhone("222-222-222")
                    .withFax("333-333-333").withEmail1("anastasya.kutnenko+1@gmail.com")
                    .withEmail2("anastasya.kutnenko+2@gmail.com")
                    .withEmail3("anastasya.kutnenko+3@gmail.com")
                    .withPhoto(photo));
        }
    }

    @Test
    public void deleteContactFromGroup() {
        app.goTo().homePage();
        Contacts contacts = app.db().contacts();
        ContactData selectedContact = contacts.iterator().next();
        Groups groups = app.db().groups();
        GroupData selectedGroup = groups.iterator().next();
        String groupName = selectedGroup.getName();
        int selectedContactId = selectedContact.getId();
        Groups beforeGroups = selectContact(selectedContactId).getGroups();
        boolean flag = false;
        try {
            assertThat(selectedContact.getGroups(),
                    Matchers.<GroupData>containsInAnyOrder(selectedGroup));
            flag = true;
        } catch (AssertionError e) {
            flag = false;
        }
        if(flag==false) {
            app.contact().addContactToGroup(groupName,selectedContactId);
        }
        app.goTo().homePage();
        app.contact().deleteContactFromGroup(groupName,selectedContactId);
        Groups afterGroups = selectContact(selectedContactId).getGroups();
        assertThat(afterGroups,
              equalTo(beforeGroups.without(selectedGroup)));

    }

    public ContactData selectContact(int contactId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object result = session.createQuery("from ContactData where id =" + contactId).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return ((ContactData)result);
    }
}
