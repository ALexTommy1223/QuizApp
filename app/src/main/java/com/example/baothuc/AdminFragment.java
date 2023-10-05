package com.example.baothuc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baothuc.adapter.CategoryAdapter;
import com.example.baothuc.databinding.FragmentAdminBinding;
import com.example.baothuc.model.CategoryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdminFragment extends Fragment {
//    private FragmentAdminBinding binding;
//    private FirebaseDatabase database;
//    private FirebaseStorage storage;
//
//    private CircleImageView categoryImage;
//    private TextInputEditText inputCategoryName;
//    private View fecthImage;
//    private AppCompatButton upload;
//
//    //
//    private ArrayList<CategoryModel> list;
//    private CategoryAdapter adapter;
//    //
//    private Dialog dialog;
//    private Uri imageUri;
//    private ProgressDialog progressDialog;
//
//    boolean isDialogShow=false;
//    public AdminFragment() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding=FragmentAdminBinding.inflate(inflater, container, false);
//
//        database=FirebaseDatabase.getInstance();
//        storage=FirebaseStorage.getInstance();
//        list=new ArrayList<>();
//
////        upload=binding.
//        dialog=new Dialog(getContext());
//        dialog.setContentView(R.layout.items_add_category);
//
//        if(dialog.getWindow()!=null){
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.setCancelable(true);
//        }
//        progressDialog=new ProgressDialog(getContext());
//        progressDialog.setTitle("Uploading");
//        progressDialog.setMessage("please wait");
//
//
//        upload=dialog.findViewById(R.id.upload);
//        inputCategoryName=dialog.findViewById(R.id.edit_category_name);
//        categoryImage=dialog.findViewById(R.id.category_image);
//        fecthImage=dialog.findViewById(R.id.view);
//
//        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
//        binding.recyclerView.setLayoutManager(layoutManager);
//        adapter=new CategoryAdapter(getActivity(),list);
//
//        binding.recyclerView.setAdapter(adapter);
//
//        database.getReference().child("categories").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    list.clear();;
//                    for (DataSnapshot dataSnapshot:snapshot.getChildren()
//                         ) {
//                        list.add(new CategoryModel(dataSnapshot.child("categoryName").getValue().toString()
//                                ,dataSnapshot.child("categoryImage").getValue().toString(),
//                                dataSnapshot.getKey(),
//                                Integer.parseInt(dataSnapshot.child("setNum").getValue().toString())
//                                ));
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//                else{
//                    Toast.makeText(getActivity(),"Category not exits",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        binding.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isDialogShow){
//                    dialog.show();
//                    isDialogShow=true;
//                }
//            }
//        });
//        fecthImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,1);
//            }
//        });
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name=inputCategoryName.getText().toString();
//                if(imageUri==null){
//                    Toast.makeText(getActivity(),"please upload category image",Toast.LENGTH_SHORT).show();
//                }
//                else if(name.isEmpty()){
//                    inputCategoryName.setError("Entern category name");
//                }
//                else{
//                    progressDialog.show();
//                    uploadData();
//                }
//            }
//        });
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                isDialogShow=false;
//            }
//        });
//        return binding.getRoot();
//    }
//
//    private void uploadData() {
//        final StorageReference reference=storage.getReference().child("category").child(new Date().getTime()+" ");
//        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                       CategoryModel model=new CategoryModel();
//
//                       model.setCategoryName(inputCategoryName.getText().toString());
//                       model.setSetNum(0);
//                       model.setCategoryImage(uri.toString());
//
//                       String uniqueKey=database.getReference().child("category").push().getKey();
//
//                       database.getReference().child("categories").child(uniqueKey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//                           @Override
//                           public void onSuccess(Void unused) {
//                               Toast.makeText(getActivity(),"Upload Success",Toast.LENGTH_SHORT).show();
//                               progressDialog.dismiss();
//                           }
//                       }).addOnFailureListener(new OnFailureListener() {
//                           @Override
//                           public void onFailure(@NonNull Exception e) {
//                               Toast.makeText(getActivity(),"Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                               progressDialog.dismiss();
//                           }
//                       });
//                    }
//                });
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1){
//            if(data!=null){
//                imageUri=data.getData();
//                categoryImage.setImageURI(imageUri);
//            }
//        }
//    }

    private FragmentAdminBinding binding;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private CircleImageView categoryImage;
    private TextInputEditText categoryName;
    private AppCompatButton upload;
    private Uri imageUri;
    private View fecthIamge;
    //
    private ArrayList<CategoryModel> list;
    private CategoryAdapter adapter;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private boolean isDialogShow=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentAdminBinding.inflate(inflater,container,false);

        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        list=new ArrayList<>();

        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.items_add_category);

        if(dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
        }
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading");
        progressDialog.setMessage("Please watting");



        categoryImage=dialog.findViewById(R.id.category_image);
        categoryName=dialog.findViewById(R.id.edit_category_name);
        upload=dialog.findViewById(R.id.upload);
        fecthIamge=dialog.findViewById(R.id.view);

        adapter=new CategoryAdapter(getActivity(),list);

        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

       database.getReference().child("categories").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                   list.clear();
                   for (DataSnapshot dataSnapshot:snapshot.getChildren()
                        ) {
                       list.add(new CategoryModel(
                          dataSnapshot.child("categoryName").getValue().toString(),
                               dataSnapshot.child("categoryImage").getValue().toString(),
                               dataSnapshot.getKey(),
                               Integer.parseInt(dataSnapshot.child("setNum").getValue().toString())
                       ));
                   }
                   adapter.notifyDataSetChanged();
               }
               else{
                   Toast.makeText(getActivity(),"Category not exit",Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
           }
       });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=categoryName.getText().toString();
                if(imageUri==null){
                    Toast.makeText(getActivity(),"please upload image",Toast.LENGTH_SHORT).show();
                }
                else if(name.isEmpty()){
                    Toast.makeText(getActivity(),"plese enter categoryname",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    uploadData();
                }
            }
        });
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDialogShow){
                    dialog.show();
                    isDialogShow=true;
                }
            }
        });
        fecthIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isDialogShow=false;
            }
        });
        return binding.getRoot();
    }

    private void uploadData() {
       final  StorageReference reference=storage.getReference().child("category").child(new Date().getTime()+" ");
       reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       CategoryModel categoryModel=new CategoryModel();
                       categoryModel.setCategoryName(categoryName.getText().toString());
                       categoryModel.setSetNum(0);
                       categoryModel.setCategoryImage(uri.toString());
                       String uniKey=database.getReference().child("category").push().getKey();
                       database.getReference().child("categories").child(uniKey).setValue(categoryModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               Toast.makeText(getActivity(),"Upload Successfully",Toast.LENGTH_SHORT).show();
                               progressDialog.dismiss();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                               progressDialog.dismiss();
                           }
                       });
                   }
               });
           }
       });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(data!=null){
               imageUri=data.getData();
               categoryImage.setImageURI(imageUri);
            }
        }
    }
}