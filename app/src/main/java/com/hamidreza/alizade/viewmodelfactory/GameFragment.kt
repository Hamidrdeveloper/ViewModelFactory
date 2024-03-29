package com.hamidreza.alizade.viewmodelfactory

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.hamidreza.alizade.viewmodelfactory.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    // The current word
    private lateinit var binding: GameFragmentBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )
        Log.i("GameFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)




        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }

        updateScoreText()
        updateWordText()
        return binding.root

    }

    /**
     * Resets the list of words and randomizes the order
     */

    /** Methods for buttons presses **/

    private fun onSkip() {
        viewModel.onSkip()
        updateWordText()
        updateScoreText()
    }

    private fun onCorrect() {
        viewModel.onCorrect()
        updateScoreText()
        updateWordText()
    }
    private fun onEndGame() {
        gameFinished()
    }
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score
        NavHostFragment.findNavController(this).navigate(action)
    }

    /**
     * Moves to the next word in the list
     */

    /** Methods for updating the UI **/

    private fun updateWordText() {
        binding.wordText.text = viewModel.score.toString()
    }

    private fun updateScoreText() {
        binding.scoreText.text =  viewModel.word
    }
}