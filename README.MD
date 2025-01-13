# Goal
Become familiar with the box2d library. Modify the snowman shape to be a full snowman. 

# Setup steps:
1. Forward port 6080
2. Open Lwjgl3Launcher.java. This file contains the main method you will use to test your code. 

# Additional Resources:
[Box2d with libgdx](https://libgdx.com/wiki/extensions/physics/box2d#initialization)

# Programming Instructions:
0. Go read the first few sections of the source above!

![alt text](image.png)
1. Try changing the following numbers within the program, and see what happens!
    -gravity
    -ball restitution (bounciness)
    -initial velocity + initial angular velocity
    -the number of snowmen in the simulation
2. Add a third snowball to the snowman! To do this, you will need to modify the addSnowman() method. Use the 2nds snowball as an example. 

3. Share! Publish, then share your link on Canvas

# Testing:
To test your code, interact with the GUI you create! Click run with the file Lwjgl3Launcher.java open. This can be found in /lwjgl3/src/main/java/bcc/snowman/lwjgl3.
The running version of your project will be available at the forwarded address from port 6080. 

# Publishing:
Run 
```
1) npm run build
2) npm i gh-pages
3) npm run deploy
```
# Submission:
To submit, publish your project on github sites. Then share the link in the canvas discussion. As a best practice, please save your work as well:
```
1) git add . 
2) git commit -m "Submitting my code"
3) git push
```
This will save your project forever and ever, so that it is not deleted with the codespace you are currently using.