package com.example.appcamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity implements IAsyncHandler{

    private ImageView imagem, imagem_rotacionada;
    private Bitmap thumbnail;
    TextView msg_gerado;
    private final int GALERIA = 1;
    private final int PERMISSAO_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        imagem = (ImageView) findViewById(R.id.setar_imagem);
        imagem_rotacionada = (ImageView) findViewById(R.id.setar_imagem_nova);
        msg_gerado = (TextView) findViewById(R.id.mensagem_gerado);
        Button galeria = (Button) findViewById(R.id.btn_selionar_image);

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GALERIA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALERIA) {
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            thumbnail = (BitmapFactory.decodeFile(picturePath));
            imagem.setImageBitmap(thumbnail);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // A permissão foi concedida. Pode continuar
            } else {
            // A permissão foi negada. Precisa ver o que deve ser desabilitado
            }
            return;
        }
    }

    public void enviar(View view){

//        int width = thumbnail.getWidth();
//        int height = thumbnail.getHeight();
//
//        int size = thumbnail.getRowBytes() * thumbnail.getHeight();
//        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
//        thumbnail.copyPixelsToBuffer(byteBuffer);
//        byte[] byteArray = byteBuffer.array();

//        String imagee = Base64.encodeToString(byteArray, Base64.DEFAULT);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, 0);
        imageString = imageString.replace("\n", "");
        Log.d("imgstring",imageString);

        try {
            Conexao task = new Conexao(MainActivity.this);
//            task.setByteArray(byteArray);
            task.execute(imageString);
        }catch (Exception ignored){
        }
        msg_gerado.setText("Sua imagem foi enviada");
    }

    @Override
    public void postResult(String result) {
        //Pega o resultado da asynctask
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(result, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imagem_rotacionada.setImageBitmap(decodedImage);
    }
}

interface IAsyncHandler {
    void postResult(String result);
}