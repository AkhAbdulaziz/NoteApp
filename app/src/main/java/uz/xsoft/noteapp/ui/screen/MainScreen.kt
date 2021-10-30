package uz.xsoft.noteapp.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import uz.xsoft.noteapp.R
import uz.xsoft.noteapp.data.entity.NoteEntity
import uz.xsoft.noteapp.databinding.ScreenMainBinding
import uz.xsoft.noteapp.ui.adapter.NoteAdapter
import uz.xsoft.noteapp.ui.viewmodel.NoteViewModel

class MainScreen : Fragment(R.layout.screen_main) {
    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()
    private val adapter by lazy { NoteAdapter() }
    private lateinit var handler: Handler
    private var querySt = ""
    private var animation: LottieAnimationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenMainBinding.bind(view)
        handler = Handler(Looper.getMainLooper())
        animation = binding.animationView
        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_addNoteScreen)
        }
        adapter.setListener { data, pos ->
            if (data.isRemoved == 1) {
                viewModel.removeData(data)
            } else{
                viewModel.updateData(data)
            }
            adapter.notifyDataSetChanged()
            viewModel.loadData()
        }
        binding.noteList.adapter = adapter
        binding.noteList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.loadData()
        viewModel.noteListLiveData.observe(viewLifecycleOwner, noteListObserver)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                query?.let {
                    querySt = it.trim()
                    if (viewModel.searchNote("%${querySt}%").isNotEmpty()) {
                        animation!!.visibility = View.GONE
                        animation!!.pauseAnimation()
                        adapter.submitList(viewModel.searchNote("%${querySt}%"))
                    } else {
                        adapter.submitList(viewModel.searchNote("%${querySt}%"))
                        animation!!.visibility = View.VISIBLE
                        animation!!.playAnimation()
                    }
                    if (query.isEmpty()) {
                        animation!!.visibility = View.GONE
                        animation!!.pauseAnimation()
                        adapter.submitList(viewModel.getAllNotes())
                    }
                    adapter.notifyDataSetChanged()
                    binding.searchView.setQuery(querySt, false)
                }
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    newText?.let {
                        querySt = it.trim()
                        if (viewModel.searchNote("%${querySt}%").isNotEmpty()) {
                            animation!!.visibility = View.GONE
                            animation!!.pauseAnimation()
                            adapter.submitList(viewModel.searchNote("%${querySt}%"))
                        } else {
                            adapter.submitList(viewModel.searchNote("%${querySt}%"))
                            animation!!.visibility = View.VISIBLE
                            animation!!.playAnimation()
                        }
                        if (newText.isEmpty()) {
                            animation!!.visibility = View.GONE
                            animation!!.pauseAnimation()
                            adapter.submitList(viewModel.getAllNotes())
                        }
                        adapter.notifyDataSetChanged()
                        binding.searchView.setQuery(querySt, false)
                    }
                }, 500)
                return true
            }
        })

        val closeButton = binding.searchView.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            adapter.submitList(viewModel.getAllNotes())
            adapter.notifyDataSetChanged()
            binding.searchView.setQuery(null, false)
            binding.searchView.clearFocus()
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private val noteListObserver = Observer<List<NoteEntity>> {
        adapter.submitList(it)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        animation = null
    }
}
