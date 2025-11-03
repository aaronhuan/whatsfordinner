# What's for Dinner
for CS501 class

----
## AI usage
ChatGPT and the built-in google gemini on android studio aided this assignment question.
The main usage was to figure out navigation's and styling.
Some examples would be:
- "How to navigate using {id} on a separate class level instead of calling it inside the component?"
- "How do I use stack in my navigation?"
- "How to style screens using Scaffold and BottomNavigation?"

I did keep a majority of the styling, as well as the process of transitioning in compose argument passing 
to the class navigation level.


## AI misunderstanding navigation
There were some misunderstandings when AI assisted.
One notable issue was when it made it so that clicking on "Home" redirected to the newly added recipe's
home page. And it would stay that page no mater how much you click on "Home" or how many recipes you add.
This was due to the incorrect usage of the stack when returning Home.
I removed this part and directly made it so that it just popupto Home route.
Additionally, it falsely assumed that I have a detailScreen.

