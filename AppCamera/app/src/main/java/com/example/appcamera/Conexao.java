package com.example.appcamera;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

public class Conexao extends AsyncTask <String, Integer, String> {
    private static final String hostname = "192.168.43.85";
    private static final int portaServidor = 12345;
    private IAsyncHandler mHandler;
    private static byte[] byteArray;


    public Conexao(IAsyncHandler mHandler) {
        this.mHandler = mHandler;
    }

//    public void setByteArray(byte[] b){
//        byteArray = b;
//    }

    @Override
    protected String doInBackground(String... params) {
        try {

            Socket socket = new Socket(hostname, portaServidor);
            //Dados enviados para o servidor
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //Arrays.toString(), pegar todas as strings que passar no método.
            bw.write(Arrays.toString(params));
            bw.newLine();
            bw.flush();

            //Dados recebidos pelo servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String retorno =  br.readLine(); //retornar ok
            socket.close();

            return retorno;
        }
        catch(IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mHandler.postResult(result);
    }
}
