package com.felipe.serialmacro;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe.lorenz
 */
public class SerialMacro {
    
    private Process process;
    private BufferedReader buffer;
    private BufferedWriter writer;
    
    public SerialMacro() throws IOException {        
        ProcessBuilder _pBuilder = new ProcessBuilder("/bin/bash");
        _pBuilder.redirectErrorStream(true);
        
        process = _pBuilder.start();
        buffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    }
    
    public String getNextLine() {
        System.out.println("l;l;l;l");
        try {
            return buffer.readLine();
        } catch (IOException ex) {
            return null;
        }
    }

    public void sendCmd( String cmd ) {
        try {
            writer.write(cmd);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {            
        }
    }
    
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		SerialMacro s = new SerialMacro();
        
        s.sendCmd("ls");
        
        Thread.sleep(1000);
        
        System.out.println(s.getNextLine());
	}
}
