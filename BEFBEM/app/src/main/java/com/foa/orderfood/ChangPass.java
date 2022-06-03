package com.foa.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foa.orderfood.Common.Common;
import com.foa.orderfood.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangPass extends AppCompatActivity {
    EditText edtPhone,edtName,edtPassword;
    Button btnSignUp;
    String sdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_pass);
        edtPhone =  findViewById(R.id.edtPhone);
        edtName =  findViewById(R.id.edtName);
        edtPassword =  findViewById(R.id.edtPassword);

        btnSignUp =  findViewById(R.id.btnSignUp);
        sdt=getIntent().getExtras().getString("SDT");
        edtPhone.setText(sdt);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference talbe_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isConnectedToInterner(getBaseContext())) {
                    final ProgressDialog mDialog = new ProgressDialog(ChangPass.this);
                    mDialog.setMessage("Vui lòng chờ...");
                    mDialog.show();

                    talbe_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                mDialog.dismiss();

                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                                talbe_user.child(edtPhone.getText().toString()).setValue(user);
                                Toast.makeText(ChangPass.this, "Bạn đổi mật khẩu thành công thành công!", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(ChangPass.this, "Vui Lòng kiểm tra lại số điện thoại!", Toast.LENGTH_LONG).show();
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(ChangPass.this,"Hãy kiểm tra kết nối Internet!",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
}
