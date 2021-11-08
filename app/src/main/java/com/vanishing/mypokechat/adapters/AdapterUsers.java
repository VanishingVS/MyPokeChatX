package com.vanishing.mypokechat.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vanishing.mypokechat.R;
import com.vanishing.mypokechat.pojos.Users;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.viewHolderAdapters> {

    List<Users> usersList;
    Context context;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public AdapterUsers(List<Users> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderAdapters onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usuarios,parent,false);
        viewHolderAdapters holder = new viewHolderAdapters(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapters holder, int position) {
        Users userss = usersList.get(position);
        final Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        holder.tv_usuario.setText(userss.getMail());

        if(userss.getId().equals(user.getUid())){
            holder.cardView.setVisibility(View.GONE);
        }else {
            holder.cardView.setVisibility(View.VISIBLE);
        }

        DatabaseReference ref_mis_botones = database.getReference("Solicitudes").child(user.getUid());
        ref_mis_botones.child(userss.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String estado = snapshot.child("estado").getValue(String.class);
                if (snapshot.exists()){
                    if (estado.equals("enviado")){
                        holder.send.setVisibility(View.VISIBLE);
                        holder.add.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.GONE);
                        holder.tengosolicitud.setVisibility(View.GONE);
                        holder.progressBar.setVisibility(View.GONE);
                    }
                    if (estado.equals("amigos")){
                        holder.send.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.VISIBLE);
                        holder.add.setVisibility(View.GONE);
                        holder.tengosolicitud.setVisibility(View.GONE);
                        holder.progressBar.setVisibility(View.GONE);
                    }
                    if (estado.equals("solicitud")){
                        holder.send.setVisibility(View.GONE);
                        holder.add.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.GONE);
                        holder.tengosolicitud.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }else {
                    holder.send.setVisibility(View.GONE);
                    holder.add.setVisibility(View.VISIBLE);
                    holder.amigos.setVisibility(View.GONE);
                    holder.tengosolicitud.setVisibility(View.GONE);
                    holder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference A = database.getReference("Solicitudes").child(user.getUid());
                A.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            A.child(userss.getId()).child("estado").setValue("enviado");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                final DatabaseReference B = database.getReference("Solicitudes").child(userss.getId());
                B.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            B.child(user.getUid()).child("estado").setValue("solicitud");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference count = database.getReference("Contador").child(userss.getId());
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Integer val = snapshot.getValue(Integer.class);
                            if (val==0){
                                count.setValue(1);
                            }else {
                                count.setValue(val+1);
                            }
                        }else {
                            count.setValue(1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                vibrator.vibrate(300);
            }//fin del onclick
        });
        holder.tengosolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference A = database.getReference("Solicitudes").child(userss.getId()).child(user.getUid());
                A.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        A.child("estado").setValue("amigos");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                final DatabaseReference B = database.getReference("Solicitudes").child(user.getUid()).child(userss.getId());
                B.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        B.child("estado").setValue("amigos");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                vibrator.vibrate(300);
            }
        });

        holder.amigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Ahora son amigos", Toast.LENGTH_SHORT).show();
            }
        });


    }//fin onbindView

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class viewHolderAdapters extends RecyclerView.ViewHolder {
        TextView tv_usuario;
        ImageView img_user;
        CardView cardView;
        Button add,send,amigos,tengosolicitud;
        ProgressBar progressBar;
        public viewHolderAdapters(@NonNull View itemView) {
            super(itemView);

            tv_usuario = itemView.findViewById(R.id.tv_user);
            cardView = itemView.findViewById(R.id.cardview);
            add = itemView.findViewById(R.id.btn_add);
            send = itemView.findViewById(R.id.btn_send);
            amigos = itemView.findViewById(R.id.btn_amigos);
            tengosolicitud = itemView.findViewById(R.id.btn_tengosolicitud);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
}
