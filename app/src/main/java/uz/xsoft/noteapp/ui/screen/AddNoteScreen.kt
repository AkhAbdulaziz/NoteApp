package uz.xsoft.noteapp.ui.screen

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import uz.xsoft.noteapp.R
import uz.xsoft.noteapp.app.App
import uz.xsoft.noteapp.data.entity.NoteEntity
import uz.xsoft.noteapp.databinding.ScreenAddNoteBinding
import uz.xsoft.noteapp.ui.viewmodel.AddNoteViewModel

class AddNoteScreen : Fragment() {
    private var _binding: ScreenAddNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddNoteViewModel by viewModels()
    private var isPinnedNow = false
    private var isSaved = false
    private var isNeedUpdate = false
    private var infoDataId = 0

    private val pref = App.instance.getSharedPreferences("myData", Context.MODE_PRIVATE)
    private var colorPickerSelected
        set(value) = pref.edit().putBoolean("colorPickerSelected", value).apply()
        get() = pref.getBoolean("colorPickerSelected", false)
    private var boldSelected
        set(value) = pref.edit().putBoolean("boldSelected", value).apply()
        get() = pref.getBoolean("boldSelected", false)
    private var italicSelected
        set(value) = pref.edit().putBoolean("italicSelected", value).apply()
        get() = pref.getBoolean("italicSelected", false)
    private var underlineSelected
        set(value) = pref.edit().putBoolean("underlineSelected", value).apply()
        get() = pref.getBoolean("underlineSelected", false)
    private var textSizeSelected
        set(value) = pref.edit().putBoolean("textSizeSelected", value).apply()
        get() = pref.getBoolean("textSizeSelected", false)
    private var textBackgroundSelected
        set(value) = pref.edit().putBoolean("textBackgroundSelected", value).apply()
        get() = pref.getBoolean("textBackgroundSelected", false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScreenAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val infoData = it.getSerializable("infoData") as NoteEntity
            infoDataId = infoData.id
            binding.editTitle.setText(infoData.title)
            binding.editor.html = infoData.message
            binding.editor.setTextColor(ContextCompat.getColor(requireContext(), R.color.mySampleColor))
            isNeedUpdate = true

            if (infoData.isPinned) {
                binding.buttonPin.setImageResource(R.drawable.ic_pin_colored)
                isPinnedNow = true
            }
        }
        binding.editor.setPadding(10, 10, 10, 10)
        binding.editor.setPlaceholder("Insert note text here...")
        binding.editor.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorTransparent
            )
        )
        binding.editor.setTextColor(Color.parseColor("#535353"))
        binding.editor.setFontSize(16)

        binding.buttonColorPicker.setOnClickListener {
            val img = it as ImageView
            if (!colorPickerSelected) {
                img.setImageResource(R.drawable.ic_color_picker_colored)
                MaterialColorPickerDialog
                    .Builder(requireContext())                            // Pass Activity Instance
                    .setTitle("Text Color")                // Default "Choose Color"
                    .setColorShape(ColorShape.SQAURE)    // Default ColorShape.CIRCLE
                    .setColorSwatch(ColorSwatch._300)    // Default ColorSwatch._500
                    .setDefaultColor("#252525")        // Pass Default Color
                    .setColorListener { color, colorHex ->
                        binding.editor.setTextColor(color)
                    }
                    .show()
            } else {
                binding.editor.setTextColor(ContextCompat.getColor(requireContext(), R.color.mySampleColor))
                img.setImageResource(R.drawable.ic_color_picker)
            }
            colorPickerSelected = !colorPickerSelected
        }

        binding.buttonTextStyleBold.setOnClickListener {
            binding.editor.setBold()
            val img = it as ImageView
            if (!boldSelected) {
                img.setImageResource(R.drawable.ic_bold_format_colored)
            } else {
                img.setImageResource(R.drawable.ic_bold_format)
            }
            boldSelected = !boldSelected
        }

        binding.buttonTextStyleItalic.setOnClickListener {
            binding.editor.setItalic()
            val img = it as ImageView
            if (!italicSelected) {
                img.setImageResource(R.drawable.ic_italic_format_colored)
            } else {
                img.setImageResource(R.drawable.ic_italic_format)
            }
            italicSelected = !italicSelected
        }

        binding.buttonTextStyleunderline.setOnClickListener {
            val img = it as ImageView
            binding.editor.setUnderline()
            if (!underlineSelected) {
                img.setImageResource(R.drawable.ic_underline_format_colored)
            } else {
                img.setImageResource(R.drawable.ic_underline_format)
            }
            underlineSelected = !underlineSelected
        }

        binding.buttonTextSize.setOnClickListener {
            val img = it as ImageView
            if (!textSizeSelected) {
                binding.editor.setFontSize(5)
                img.setImageResource(R.drawable.ic_format_size_colored)
            } else {
                binding.editor.setFontSize(3)
                img.setImageResource(R.drawable.ic_format_size)
            }
            textSizeSelected = !textSizeSelected
        }

        binding.buttonBackgroundText.setOnClickListener {
            val img = it as ImageView
            if (!textBackgroundSelected) {
                img.setImageResource(R.drawable.ic_text_bacground_colored)
                MaterialColorPickerDialog
                    .Builder(requireContext())              // Pass Activity Instance
                    .setTitle("Highlight Color")                // Default "Choose Color"
                    .setColorShape(ColorShape.SQAURE)    // Default ColorShape.CIRCLE
                    .setColorSwatch(ColorSwatch._300)    // Default ColorSwatch._500
                    .setDefaultColor(R.color.secondColor)    // Pass Default Color
                    .setColorListener { color, colorHex ->
                        binding.editor.setTextBackgroundColor(color)
                    }
                    .show()
            } else {
//                binding.editor.setTextBackgroundColor(R.color.secondColor)
                img.setImageResource(R.drawable.ic_text_bacground)
            }
            textBackgroundSelected = !textBackgroundSelected
        }

        binding.buttonPin.setOnClickListener {
            val img = it as ImageView
            if (!isPinnedNow) {
                img.setImageResource(R.drawable.ic_pin_colored)
            } else {
                img.setImageResource(R.drawable.ic_pin)
            }
            isPinnedNow = !isPinnedNow
        }

        binding.buttonSave.setOnClickListener {
            saveNote()
            isSaved = true
            findNavController().popBackStack()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveNote() {
        var title: String

        if (binding.editor.html != null && (binding.editor.html.trim()
                .isNotEmpty() || binding.editTitle.text.toString().isNotEmpty())
        ) {
            if (binding.editTitle.text.toString().isEmpty()) {
                title = "New Note"
            } else {
                title = binding.editTitle.text.toString()
            }
            if (isNeedUpdate) {
                viewModel.updateNote(
                    NoteEntity(
                        infoDataId,
                        title,
                        binding.editor.html,
                        System.currentTimeMillis(),
                        isPinnedNow,
                        0
                    )
                )
            } else {
                viewModel.addNote(
                    NoteEntity(
                        0,
                        title,
                        binding.editor.html,
                        System.currentTimeMillis(),
                        isPinnedNow,
                        0
                    )
                )
            }
        } else if (binding.editTitle.text.toString().isNotEmpty()) {
            if (isNeedUpdate) {
                viewModel.updateNote(
                    NoteEntity(
                        infoDataId,
                        binding.editTitle.text.toString(),
                        binding.editor.html,
                        System.currentTimeMillis(),
                        isPinnedNow,
                        0
                    )
                )
            } else {
                viewModel.addNote(
                    NoteEntity(
                        0,
                        binding.editTitle.text.toString(),
                        " ",
                        System.currentTimeMillis(),
                        isPinnedNow,
                        0
                    )
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (!isSaved) {
            saveNote()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}