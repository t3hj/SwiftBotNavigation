# SwiftBot Navigation

A Java robotics coursework project that reads navigation commands from QR codes
and controls a SwiftBot. It demonstrates command parsing, hardware interaction,
movement history and file-based logging.

**Hardware required:** this is not a browser demo. Running the program needs a
compatible SwiftBot/Raspberry Pi setup and the SwiftBot Java API.

## Source map

| File | Responsibility |
| --- | --- |
| `SwiftBotNavigate/src/Main.java` | Entry point, camera loop and button handling |
| `QRCodeProcessing.java` | QR capture and decoding |
| `CommandProcessing.java` | Command validation and dispatch |
| `Movements.java` | Movement and stop operations |
| `RetraceMovements.java` | Movement-history replay |
| `CommandLogger.java`, `WritingLog.java` | Command history and log writing |

## Build

Install a JDK compatible with the supplied SwiftBot API on the development
machine. From the repository root, on macOS/Linux:

```sh
cd SwiftBotNavigate
mkdir -p bin
javac -cp 'lib/*' -d bin src/*.java
```

In PowerShell, create `bin` if absent and use `javac -cp "lib/*" -d bin src/*.java`.
On the configured robot, with classes and `lib/` available:

```sh
java -cp 'bin:lib/*' Main
```

The classpath separator is `;` on Windows. Desktop compilation alone does not
verify camera capture or motor behaviour. The historical Ant target packages
existing `bin/` classes and uploads via SCP; it does not compile the source.
It uses `trust="true"`, so prefer a separately verified SSH connection instead
of treating it as a secure deployment recipe. Do not commit robot credentials.

## QR commands

Use uppercase command letters and a single space between arguments.

| Command | Intended behaviour |
| --- | --- |
| `F 2 50` | Move forward for 2 seconds at speed 50 |
| `B 2 50` | Move backward for 2 seconds at speed 50 |
| `L 2 50` | Fixed left turn followed by forward movement |
| `R 2 50` | Fixed right turn followed by forward movement |
| `T 2` | Retrace previous movements |
| `W` | Write the command log |
| `A` | Request the stop operation |

Movement durations are checked against 1–6 seconds. The QR pattern accepts
non-negative numeric speed inputs; use 0–100. Although the regex is
case-insensitive, later validation/dispatch is case-sensitive, so lowercase
commands are not reliably supported.

## Known limitations and next steps

- No automated tests or verified clean-build CI are included yet.
- Hardware behaviour must be checked on a clear test surface with a physical
  stop available; QR parsing is not a safety-rated stop mechanism.
- Large numeric inputs can throw during parsing. Normalise case/whitespace and
  reject out-of-range values before dispatch.
- Retrace counting currently uses the overall command log, including control
  commands. Separate movement history before relying on it for path reversal.
- Left/right turns use fixed motor values and timing rather than calibrated angles.

A useful next refactor is a pure command parser with unit tests, separated from
the SwiftBot hardware adapter. That would make validation testable without a robot.
