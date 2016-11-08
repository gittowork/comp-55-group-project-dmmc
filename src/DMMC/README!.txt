This is the Repository we will be working in for the COMP 055 project.

Make sure you git init in the "src" folder in your Eclipse project. 
This is to make sure we only share the .java code files and nothing else. 

Note: Make sure you pull other peoples changes before pushing your own. 

Git Commands:
	Commands to init:
	1)git init
	2)git remote add origin https://github.com/pranavt97/DMMC.git
	3)git pull origin master
	
	Commands to add and commit changes:

	1)git add . 
	2)git commit -m "Your Message Here"
	3)git push origin master
	
	Commands to pull changes:
	
	1)git pull origin master
	
	Commands to merge(might need this to combine changes): 
	1) git merge

ACM:
	To add acm to your project
	1)copy the ACM.jar file from the Traffic Jam project folder. 
	2)paste it in a new lib folder in this project
		-The lib folder should be in the same directory as the src, bin, and .settings folders
	3)Open the project in eclipse
	4)right click project under Package Explorer and select Refresh (Making sure lib folder appears)
	5)right click project under Package Explorer and select Properties
	6)Go to the Libraries tab under the Java Build Path settings
	7)Select the Add JARs button and find the ACM.jar file under the lib folder
		-if the lib folder is missing, check step 4

	NOTE: We can add other external jar files like this