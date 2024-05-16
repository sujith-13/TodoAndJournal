package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Journal_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Journal_fragment : Fragment() {
    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser

    // Firebase Firestore
    private val db = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = db.collection("Journal")

    // Firebase Storage
    private lateinit var storageReference: StorageReference

    // List of Journals
    private val journalList: MutableList<Journal> = mutableListOf()

    // RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: JournalListAdapter

    // Widgets
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_journal_fragment, container, false)

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
           user=firebaseAuth.currentUser!!

        // Widgets
        recyclerView = view.findViewById(R.id.rv)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fab = view.findViewById(R.id.fab)

        fab.setOnClickListener {
            val intent = Intent(requireActivity(), AddJournalActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
                 inflater.inflate(R.menu.my_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemId = item.itemId

        if (itemId == R.id.action_add) {

            if (user != null && firebaseAuth != null) {
                val intent = Intent(requireActivity(), AddJournalActivity::class.java)
                startActivity(intent)
            }
        } else if (itemId == R.id.action_signout) {
            if (user != null && firebaseAuth != null) {
                firebaseAuth.signOut()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        collectionReference.whereEqualTo("userId",user.uid).get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                    journalList.clear()
                for (journals in queryDocumentSnapshots) {
                    val journal = journals.toObject(Journal::class.java)
                    journalList.add(journal)
                }

                myAdapter = JournalListAdapter(requireContext(), journalList)
                recyclerView.adapter = myAdapter
                myAdapter.notifyDataSetChanged()

            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    "Opps! Something went wrong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}