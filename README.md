# 🤖 SwiftBot Navigation Program

### Overview
This program guides the SwiftBot to navigate a space using commands provided via QR codes. The SwiftBot begins its journey by scanning a QR code for directions, with subsequent stops offering additional QR codes for navigation. The program includes command validation and user-friendly error handling.

### Features
- **QR Code Command Recognition** 📱: Reads navigation commands from QR codes.
- **Movement Commands**:
  - `F <duration> <speed>`: Move forward for the specified duration (seconds) and speed (up to 100).
  - `B <duration> <speed>`: Move backward similarly.
  - `R <duration> <speed>`: Turn right for the specified duration and speed.
  - `L <duration> <speed>`: Turn left for the specified duration and speed.
- **Retrace Movement** 🔄: Command `T <number>` retraces the last specified movements, with error handling for exceeding executed commands.
- **Logging Commands** 📝: Command `W` writes the current time and all received commands to a text file.

### How to Use
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/t3hj/SwiftBotNavigation.git
   ```
2. **Navigate to the Project Directory**:
   ```bash
   cd SwiftBotNavigation
   ```
3. **Run the Program**: 
   - Execute the main script in your preferred environment.

### Command Structure
- **Movement Commands**:
  - `F <duration> <speed>`: Move forward for the specified duration and speed.
  - `B <duration> <speed>`: Move backward for the specified duration and speed.
  - `R <duration> <speed>`: Rotate right for the specified duration and speed.
  - `L <duration> <speed>`: Rotate left for the specified duration and speed.
- **Retrace Command**:
  - `T <number>`: Retraces the last specified number of movements.
- **Write Log Command**:
  - `W`: Writes the current time and all commands to a text file.

### Error Handling
- Input values are validated to ensure they are within specified limits.
- User-friendly error messages guide input corrections.

### Future Improvements
- Enhance QR code recognition capabilities for complex commands.
- Integrate obstacle detection and avoidance features.
- Implement a graphical interface for real-time navigation visualization.

### Contributing
Feel free to submit a pull request if you'd like to contribute to the development of the SwiftBot Navigation Program! 🤝
