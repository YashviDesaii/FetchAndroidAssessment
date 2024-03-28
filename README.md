# Fetch Android Assignment

## Project Description
This project retrieves data from a specific URL (https://fetch-hiring.s3.amazonaws.com/hiring.json) and displays it to the user grouped and sorted based on certain criteria.

## Requirements
- Fetch data from the specified URL
- Group items by "listId"
- Sort results by "listId" and then by "name"
- Filter out items with blank or null "name"
- Display the final result in an easy-to-read list to the user

## Technologies Used
- Programming Language: Kotlin
- Libraries/Frameworks: JSON parsing, RecyclerView 

## Build Instructions
1. Clone the repository locally: `git clone https://github.com/your-username/FetchAndroidAssignment.git`
2. Open the project in Android Studio (or your preferred IDE)
3. Build the project using the latest tools and SDK versions
4. Run the app on a compatible Android device or emulator

## Usage Instructions
1. Open the app on your device
2. View the list Ids grouped and sorted together
3. Click on a list Id to view the item names within that list id. Item names have been sorted based on the Integer present in the name. Eg Item 2 comes before Item 11
