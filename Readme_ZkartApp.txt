Deployment: The application is developed in android studio.Thus, the deployable apk file is also generated and uploaded in github.(Local directory = ZKart\app\build\outputs\apk)
The mobile app asks the user to login or create a new account (The password is encrypted with a simple string conversion)

Sample accounts for verification

(User)
Username: sampleuser
password: sample
(Admin)
username: admin@zoho.com
password: admin

1. User can login to the app, view items and add number of items to the cart
2. User is allowed to view the cart and purchase the desired product (If the required quantity is more than the available stock, the user cannot buy)
3. User is also allowed to view the purchase history.

1. Admin is allowed to add stocks of each item 
2. If the stock count is below threshold (i.e 10), admin will be indicated with a red card view
3. Admin can also add new items to the database 

Technologies Used:
1. Android studio (Java, XML)
2. Firebase