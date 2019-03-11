package mypackage.addressbook;

import org.testng.annotations.*;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {

        goToGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("test1", "test1", "test1"));
        submitGroupForm();
        returnToGroupPage();
    }

}
