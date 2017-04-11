# zPuzzle

zPuzzle is a traditional jigsaw puzzle with a twist. In a span of 4 week sprints cycle, we have the following functionalities implemented:

## 1. Log-in: 
The user can register their email address and log-in with that account. Or they can use their facebook account to log-in.

## 2. Level selection: 
This sets the difficulty level for the puzzle.

    i. **Easy**: 3X3 puzzle.
    ii. **Medium**: 4X4 puzzle.
    iii. **Hard**: 5X5 puzzle.
    
## 3. Image selection: 
Modes with which images can be used to be made into a puzzle.

    i. **Click a picture**: Using the camera, a picture can be clicked instantly & made into a puzzle.
    ii. **Upload from gallery**: User can choose any picture from their phone gallery to be made into a puzzle.
    iii. **Random images**: User can choose one of the preloaded images in the app.
    
## 4. Game activity: 
The user can solve the chosen puzzle or can choose one of the preloaded images during the game should they change their 
mind. While the puzzle is being solved, a timer keeps running to keep an account of the time taken to solve the puzzle. And also the number of movements made by the user are tracked.

## 5. Game completion: 
Upon completing the game, the user can either

    i. Reload the image and solve the puzzle again. OR
    ii. Choose to save the history in the DB. OR
    iii. Navigate back to the main activity (level selection).
    
## 6. Navigation bar: 
Upon sliding the left side of the main activity a menu is presented, which contains:

    **1. User profile**: 
    The user can change their profile picture, update the password of their account. Not applicable to facebook log-in.
    
    **2. History**: 
    The user can view the historical stats of their game: Image puzzle solved, level of the game, number of moves to complete
    the game and number of moves to complete  the game.
    
    **3. Send invitation to a friend to solve the puzzle**: 
    An invitation to solve the puzzle can be sent to a friend. Along with the invitation a surprise message can be set which will be 
    revealed post solving the puzzle.
    
    **4. Inbox**: 
    All the invitations sent will reside here.
    
    **5. Sent**: 
    A list of sent invitations can be found here.
    
    **6. Settings**: 
    A provision for the user to play/mute the background music. Other functionalities will be added later.
    
    **7. Logout**: 
    To log out the existing user. The app remembers the last user logged in even if the app was closed.
