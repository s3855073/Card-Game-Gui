Hi,

I will be outlining how the CardGame and CardGameGUI was built, how to set up the build path between the two projects,
and how to run the application.

CardGame and CardGameGUI were built by myself, Vincent Tso, as assignment projects for my Java programming class, Further Programming.
I have used MVC principles and maximised cohesion whilst minimising coupling between different classes. All building blocks, the models,
views, and controllers were hand-made by me, and I've used final variables to help avoid magic numbers in the code.



First, we begin by making sure that each project is using the correct system library. For each project, right click on the project and select
the Configure Build Path... option under Build Path, as follows:

CardGame > Build Path > Configure Build Path...

Once the new window opens, we need to go under the Libraries tab and make sure that the current Java System Library is version 1.8. If not,
we need to hit the Add Library button, select the correct Java System Library (which should be already selected), and click Finish.

Afterwards, we need to hit apply and close to confirm the selection.

Repeat the process for CardGameGUI, and we should have successfully updated or verified the correct Java System Library for both projects
to use.



To set up the build path between the two projects, we need to right-click on the CardGameGUI project, and click Configure Build Path
under the Build Path option as follows:

CardGameGUI > Build Path > Configure Build Path...

Once the new window opens, we need to go under the Projects tab and click Add... if the CardGame project isn't already present in the linked
build path.

Once we've chosen the CardGame project, we need to hit the apply and close button to confirm the selection.

After this we should have successfully linked the CardGameGUI back to the CardGame project, and thus CardGameGUI should be able to call
on the necessary imports that are required for it to run the application.



In order to run the application, we need to open up the src folder in the CardGameGUI project, open up the client package, and right click 
Run As Java Application on the AppRunner.java as follows:

CardGameGUI > src > client > AppRunner.java > Run As > Java Application

This should create the Game Engine object, the necessary GameEngineCallback objects, and the AppFrame object to build the GUI.
You will notice that I have passed through the AppFrame object to each necessary Panel and EventListener, with each class containing
appropriate getter methods so that each class only has access to the necessary components it needs to use.



As of current, there are is a big where if you input the maximum integer possible as the player's point value, and then bet that same point value,
if the player wins then he will be awarded -900000+ points. I have assumed that this is an issue with the Integer object, and I have since
regarded this as no bugs in both projects, assuming successful Java System Libraries and Build Paths are correctly set up.

I have manually hand-drawn the cards on the Card Panel component using if statements to check whether the currently selected player should be
viewing their own cards, and used basic geometry and math to scale the drawn cards to size and align their following suits and value strings
in the correct spaces.

If the player busts, I grey out their last dealt card to signify that the last card was the bust card. This also applies to the House as well.



Thanks for taking the time to read this and understand how this application works. I look forward to hearing back from you and seeing how all
of these hours of work compared to the real deal! 

Kind regards,
Vincent.
