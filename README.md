# JavaProcessor
An implemented processor written in Java with his own Assembler

NOTE: The Assembler is not written! I am still working on it :)

# Classes:
  ALU
  
  Register
  
  Processor
# ALU members:
  ALU post ALU            - The ALU that comes after
  
  boolean cOut            - The Carry Out (so far useless, but it can be usefull later)
  
  boolean state           - The Output
  
  public boolean cIn      - The Carry In
  
  public boolean a        - Input a
  
  public boolean b        - Input b
  
  public boolean[2] ALUOp - The control signal that the ALU needs to know what to do (always size = 2)
# ALU methods:
  ALU(ALU postALU)        - ALU constructor, postALU can be equal to null
  
  void processSignals()   - Lets the ALU calculate state (and cOut) based on the inputs and control signals
  
  void printState()       - Prints the current state and cOut
# Processor members:
  ALU[16] alus            - All ALUs
  
  Register[16] regs       - All Registers
# Processor methods:
  Processor()                                   - Builds the processor (all sizes are fixed, but in theory can be changed)
  
  void add(boolean[16] a, boolean[16] b)        - Adds two 16 bit numbers
  
  void and(boolean[16] a, boolean[16] b)        - Logic AND for two 16 bit numbers
  
  void or(boolean[16] a, boolean[16] b)         - Logic OR for two 16 bit numbers
  
  void printState()                             - Prints the 16 bit output
  
  static boolean[] StringTo16Bit(String binary) - Converts a String of size 16 to a boolean array of same size
# Register members:
  boolean[16] bits  - The binary number stored in the register
# Register methods:
  Register(int size)            - Builds Register and allocates memory. In theory, size can be not equal 16, but it should always be 16
  
  boolean[16] load              - Returns the stored 16 bit number
  
  void safe(boolean[16] value)  - Stores a  16 bit number
