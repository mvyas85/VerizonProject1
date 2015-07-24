# VerizonProject

Requirment ::
=============

Build an Android application that allows a customer to enter text that will be treated as a "Tag". Tags should be saved to the device so they can be  viewed/edited at any  time. Text for a tag can contain alpha numeric characters  and no special characters.  

As a further requirement, your boss wants you to provide a library that can be  used in Android projects that would provide the following functionality:  

    1. Given a set of Tags, sort the Tags in ascending or descending order. 
    2. Given a set of Tags, determine the most frequently occurring Tag.

In your application, provide a second screen that provides the requirements below; customers should be able to navigate between screens at any time:  

    1. Displays all the stored Tags and shows a count of the Tags 
    2. Customer can perform an action to display Tags in ascending and/or descending order 
    3. Customer can perform an action that will duplicate the current set of Tags     1,000 times. Customer should see the count reflected accurately. 
    4. Customer can perform an action that will display the most frequently occurring     Tag when action is complete. While this is happening, the customer    should still be able to interact with the listing of all Tags.   

Application must support portrait and landscape orientations.  Application must run on Gingerbread and newer versions of Android.


Detail:

Snapshots ::
============
This Screen is First Activity in your Applicaiton ::

Only Allowed Text is [a-z] [A-Z] [0-9] space or back

![Image of Yaktocat](https://github.com/mvyas85/VerizonProject1/blob/master/images/2.png)

By clicking Insert My Tag Button you will be able to insert your Tag in to the SQLite Database-
Clicking on Red All Tag will take you to next Activity where you can see all the TAGs

![Image of Yaktocat](https://github.com/mvyas85/VerizonProject1/blob/master/images/1.png)

(To go back to previous screen click on Back Arrow) on Action Menu Bar.
There are three buttons on the top of screen 
     1. Duplicate TAG(s) 1000 Times - will let you duplicate desired Tag 1000 times
     2. Most Frequent TAG Button - will let you see which TAG is used added number of Times.
     3. Descending/ Ascending Order Toggle Button - will let you organize your list into Descending/ Ascending Order 

![Image of Yaktocat](https://github.com/mvyas85/VerizonProject1/blob/master/images/3.png)

When you click on Edit Button near your Tag it will let you Edit your desired TAG text.
By Pressing Enter ↵ button on right will Update your Entry.

![Image of Yaktocat](https://github.com/mvyas85/VerizonProject1/blob/master/images/4.png)

After every successful update it will show you Toast that entry has been updated and it will refresh TOTAL TAG COUNT as well as the Cout of your TAG

![Image of Yaktocat](https://github.com/mvyas85/VerizonProject1/blob/master/images/5.png)

By clicking on MOST FREQUENT TAG will show you dialog with highest used TAG
- In case of multiple TAGs with same Count it will show you FIRST Tag in the Database ( No specific requirment given ! - can update if requirments occure )

![Image of Yaktocat](https://github.com/mvyas85/VerizonProject1/blob/master/images/6.png)

Since you are Duplicating Entry 1000 times ( for each selected TAG) it may take few seconds to complete Transaction, So showing Loading Dialog while DB is inserting Entries - for better user Experience.

![Image of Yaktocat](https://github.com/mvyas85/VerizonProject1/blob/master/images/7.png)
