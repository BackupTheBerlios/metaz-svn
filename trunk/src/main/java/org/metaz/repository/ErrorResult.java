// A "error result" class to describe outcomes of "actions"

// Author: Falco Paul

package org.metaz.repository;

import java.util.Stack;

public class ErrorResult extends ActionResult  {

  private String message = null;
  private StackTraceElement caller = null;
  private Stack errorStack = null;

  // empty constructor

  public ErrorResult() {
  
    setMessage(new String("Error"));
    
  }

  // constructor with an included message

  public ErrorResult(String setMessage) {
  
    setMessage(setMessage);
    
  }

  // constructor with an included message AND all previously occurred errors

  public ErrorResult(String setMessage, Stack setErrorStack) {
  
    setMessage(setMessage);
    setErrorStack(setErrorStack);
    
  }

  // message getter

  public String getMessage() {
  
    StringBuffer stackMessage = new StringBuffer();
    
    if (errorStack != null) {

      Stack clone = (Stack) errorStack.clone();
      
      while (! clone.isEmpty()) {

        stackMessage.append( ((ErrorResult) clone.pop()).getMessage() );
        
        if (! clone.isEmpty())
          stackMessage.append(":");
        
      }
      
      if (stackMessage.length() != 0)
        stackMessage.insert(0, ":");
      
    }
  
    return message + stackMessage.toString();
    
  }

  // message setter

  public void setMessage(String setMessage) {
  
    message = setMessage;
    
    // Warning: trace[0] is the line below!

    StackTraceElement[] trace = new Throwable().getStackTrace();
    
    int index = 1;
    
    // Now we must find the first entry that's not originating from this class!
    
    while (trace[index].getClass() == this.getClass())
      index++;
    
    setCaller(trace[index]);
    
  }

  // caller getter

  public StackTraceElement getCaller() {
  
    return caller;
    
  }

  // caller setter

  public void setCaller(StackTraceElement setCaller) {
  
    caller = setCaller;
    
  }

  // error stack getter

  public Stack getErrorStack() {
  
    return errorStack;
    
  }

  // error stack setter

  public void setErrorStack(Stack setErrorStack) {
  
    errorStack = setErrorStack;
    
  }

  // toString() implementation
  
  public String toString() {
  
    return getMessage();
    
  }

}
