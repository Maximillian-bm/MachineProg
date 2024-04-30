# Machine Oriented Programming Project 2



## Cover page
Cover page with project name, student names and photos

## Contributions
In the very beginning of the report, mention the contribution of each group member to the project work, both in terms of project writing but also code writing (Group reports are individualized). Consider generative AI(for example chatGPT) as a group member and credit it for its contribution to the group work.

## Requirement specifications
### Functional Requirements
#### Text-Based Version
* Implement the game using a text-based interface in the terminal window.

* Display cards and game layout as described in the project specifications, including columns, foundations, and hidden cards.

* Support commands for loading a deck, shuffling the deck, displaying the deck, initiating the game, and quitting the program.

* Enable gameplay by allowing players to move cards within the tableau and to the foundation piles following the rules described in the project specifications. // Definér regler

* Implement move commands to facilitate gameplay, ensuring validation of moves and appropriate error messages when invalid moves are attempted.

* Support saving and loading the game state to/from files, following specified formats.

* Provide error handling for invalid input commands to ensure the program does not crash.


#### Graphical User Interface (GUI) Version
* Develop a GUI using either C and SDL or a different programming language as specified in the project description.

* Ensure that the GUI version retains the core game logic of the text-based version.

* Display the game elements, including cards, columns, and foundations in a visually appealing and intuitive interface.

* Enable user interaction through mouse clicks or touch gestures, allowing players to move cards and execute game commands.

* Implement event handling to respond to user actions and update the game state accordingly.

* Provide options for saving and loading the game state within the GUI interface.

### Non-Functional Requirements
#### Performance:
* Ensure efficient memory management and minimal resource usage to provide smooth gameplay and responsiveness, especially in the GUI version.

* We want to make sure the algorithms for shuffling and validating moves are efficient enough to maintain fast response times.


#### Usability:
* Design the user interface to be intuitive and easy to navigate, with clear labels and visual cues for game elements.

* Provide feedback to the player through messages and prompts, indicating the result of each action and guiding the player's next steps. // Show command syntax when the user inputs wrong arguments.


#### Portability
* Ensure that the codebase is portable across different platforms, allowing the game to run seamlessly on various operating systems. // Bare windows og mac.

* If using external libraries or frameworks, ensure compatibility with different environments and versions.


#### Documentation
* Document the code thoroughly, including comments and documentation strings, to aid understanding and maintainability.

* Writing the report.

### Testing Requirements
* Develop comprehensive test cases covering all aspects of the game logic, including loading decks, shuffling, gameplay rules, and error handling.
  Perform unit tests to verify the functionality of individual components and integration tests to validate interactions between modules.
* Comprehensive testing will be conducted to ensure the correctness and robustness of the game implementation.




### Extra stuff to add
#### Testing
* Conduct user acceptance testing (UAT) to gather feedback from actual players and ensure the game meets their expectations in terms of usability and enjoyment.
* Implement automated testing frameworks and tools to streamline the testing process and maintain code quality throughout development. By adhering to these requirement specifications, the implementation of the Yukon card game using linked lists in C can be robust, user-friendly, and adaptable to future enhancements.


## Analysis

### Overall Structure
We want to organize the game into modular components such as the game engine, input/output handling, and game logic to facilitate easier management and maintenance. This modular approach allows for clear separation of concerns, making it easier to understand and modify specific parts of the game without affecting others. It also promotes code reusability and scalability, enabling the addition of new features or enhancements in the future. By breaking down the game into smaller, manageable modules, we can improve collaboration among developers working on different aspects of the game and streamline the development process overall.

### Game Engine
We want to develop a game engine responsible for managing the overall flow of the game, including initialization, main game loop, and termination. The game engine serves as the central control hub, coordinating interactions between different modules and handling high-level game states. It initializes the game environment, sets up necessary resources, and orchestrates the execution of game logic and input/output operations. The main game loop ensures continuous gameplay by repeatedly updating the game state based on user input and internal events. Proper design and implementation of the game engine are crucial for ensuring smooth and consistent gameplay experience across different platforms and environments.

### Input/Output Handling
We want to implement an input handling module that captures user input from the terminal window. This module is responsible for interpreting user commands, such as moving cards or initiating game actions, and translating them into actionable instructions for the game logic. It should provide a robust input validation mechanism to prevent invalid inputs and guide the player towards correct usage. Additionally, we aim to create an output handling module to display game information, including card layouts, messages, and prompts. This module should present information in a clear and intuitive manner, using text-based representations or ASCII art to visualize game elements and provide feedback to the player. Effective input/output handling is essential for ensuring a seamless and immersive gaming experience that keeps players engaged and informed throughout their gameplay session.

### Game Logic
We want to implement a game logic module that accurately represents the rules and mechanics of the Yukon card game. This module serves as the backbone of the game, orchestrating various aspects such as deck management, card movements, and win conditions. It should enforce the game rules consistently and fairly, validating player actions and updating the game state accordingly. The game logic module should handle tasks such as dealing cards, moving cards between columns and foundation piles, and checking for victory conditions. It should also handle edge cases and special scenarios gracefully, providing informative feedback to the player and ensuring a satisfying gameplay experience. By implementing robust and well-tested game logic, we can create a compelling and enjoyable gaming experience that challenges players and encourages strategic thinking.

### Data Structures
We want to select appropriate data structures, such as arrays, linked lists, or custom structures, to represent cards, columns, and foundation piles. The choice of data structures plays a critical role in determining the efficiency and performance of the game implementation. We aim to choose data structures that optimize memory usage and facilitate fast access and manipulation of game elements. For example, using arrays or linked lists to represent columns of cards allows for efficient traversal and manipulation of card sequences, while using a stack or queue data structure for the deck and foundation piles simplifies card insertion and removal operations. Additionally, we may employ advanced data structures such as trees or graphs for more complex game mechanics or optimizations. By carefully selecting and implementing appropriate data structures, we can improve the overall efficiency and responsiveness of the game while minimizing resource usage.

### Algorithms
We want to develop a shuffling algorithm that randomizes the order of cards in the deck, ensuring fair and unpredictable gameplay. The shuffling algorithm should thoroughly shuffle the deck to eliminate any biases or patterns, ensuring that each game session starts with a truly randomized deck arrangement. We aim to implement efficient shuffling algorithms, such as the Fisher-Yates shuffle algorithm, that achieve uniform randomness while minimizing computational overhead. Additionally, we aim to implement move validation algorithms that verify the legality of moves, preventing invalid moves and ensuring adherence to game rules. These algorithms should accurately assess the validity of player actions, taking into account the current game state and the specific rules of the Yukon card game. By implementing robust and efficient algorithms, we can ensure a fair and challenging gameplay experience that keeps players engaged and entertained.

### Error Handling
We want to implement robust error handling mechanisms to detect and gracefully handle invalid inputs and unexpected conditions. Error handling is essential for maintaining the stability and reliability of the game, preventing crashes and unexpected behavior that could disrupt the player experience. We aim to implement thorough input validation checks to catch common errors such as invalid commands or out-of-range inputs. Additionally, we aim to provide informative error messages that guide the player towards correct usage and help them understand why their input was invalid. Error handling should cover a wide range of potential issues, including file I/O errors, memory allocation failures, and unexpected game states. By implementing comprehensive error handling mechanisms, we can enhance the overall stability and usability of the game, providing a smoother and more enjoyable experience for players.

### File I/O
We want to incorporate file input/output operations to enable saving and loading game states to/from files. File I/O functionality allows players to save their progress and resume their game sessions at a later time, enhancing the replay value and longevity of the game. We aim to define a specified file format for storing game state data, ensuring compatibility and consistency across different platforms and environments. The file format should be simple and easy to parse, allowing for efficient read and write operations. Additionally, we aim to implement error handling mechanisms to handle file I/O errors gracefully, providing informative feedback to the player in case of issues such as file corruption or disk full errors. By incorporating file I/O functionality, we can provide players with the flexibility to save and manage their game progress, enhancing their overall gaming experience.

### Memory Management
We want to employ efficient memory management techniques to optimize memory usage and prevent memory leaks. Effective memory management is essential for maintaining the performance and stability of the game, especially during prolonged gameplay sessions or on systems with limited resources. We aim to use dynamic memory allocation and deallocation judiciously, allocating memory only when necessary and releasing it promptly when no longer needed. We also aim to implement error handling mechanisms to detect and handle memory allocation failures gracefully, preventing crashes and unexpected behavior. Additionally, we aim to minimize memory fragmentation and overhead by using appropriate data structures and memory allocation strategies. By implementing efficient memory management techniques, we can ensure the game runs smoothly and reliably, providing a seamless and enjoyable experience for players.



## Design
(how? - paper solution – flow diagrams/pseudocode – before writing the code)

## Implementation
(how? - actual solution – after writing the code)

## Tests
(Does it work? - prove that the requirements are fulfilled)

## AI usage
Discussion about the use of generative AI in your project. What is it good for, what it’s not good for, how did it help you in the project work and with learning.

## Conclusion
(Summary of the highlights from the report)

## How to run the game

## Appendix
(what doesn’t fit in the report – extra diagrams, test reports etc.)
