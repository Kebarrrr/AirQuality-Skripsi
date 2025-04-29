package com.skripsi.airquality.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.skripsi.airquality.R

class AboutFragment : Fragment() {

    private val aboutViewModel: AboutViewModel by viewModels()

    private lateinit var profileImage: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var jobTitleTextView: TextView
    private lateinit var bioTextView: TextView
    private lateinit var projectsCreatedTextView: TextView
    private lateinit var skillsTextView: TextView
    private lateinit var experienceTextView: TextView
    private lateinit var emailButton: Button
    private lateinit var linkedinButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_about, container, false)

        profileImage = binding.findViewById(R.id.profileImage)
        nameTextView = binding.findViewById(R.id.name)
        jobTitleTextView = binding.findViewById(R.id.jobTitle)
        bioTextView = binding.findViewById(R.id.bio)
        projectsCreatedTextView = binding.findViewById(R.id.projectsCreated)
        skillsTextView = binding.findViewById(R.id.skills)
        experienceTextView = binding.findViewById(R.id.experience)
        emailButton = binding.findViewById(R.id.emailButton)
        linkedinButton = binding.findViewById(R.id.linkedinButton)

        aboutViewModel.name.observe(viewLifecycleOwner) { name ->
            nameTextView.text = name
        }
        aboutViewModel.jobTitle.observe(viewLifecycleOwner) { jobTitle ->
            jobTitleTextView.text = jobTitle
        }
        aboutViewModel.bio.observe(viewLifecycleOwner) { bio ->
            bioTextView.text = bio
        }
        aboutViewModel.projectsCreated.observe(viewLifecycleOwner) { projectsCreated ->
            projectsCreatedTextView.text = projectsCreated
        }
        aboutViewModel.skills.observe(viewLifecycleOwner) { skills ->
            skillsTextView.text = skills
        }
        aboutViewModel.experience.observe(viewLifecycleOwner) { experience ->
            experienceTextView.text = experience
        }

        emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:akbarnugrahad02@gamil.com") // Replace with actual email
            startActivity(Intent.createChooser(intent, "Send Email"))
        }

        linkedinButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.linkedin.com/in/akbarnugrahadimyati/") // Replace with actual LinkedIn URL
            startActivity(intent)
        }

        aboutViewModel.loadProfileData()

        Glide.with(this)
            .load(R.drawable.profile_formal)
            .transform(RoundedCorners(25))
            .into(profileImage)

        return binding
    }
}
