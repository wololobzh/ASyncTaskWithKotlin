package fr.acos.asynctaskwithkotlin

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Fonction permettant de voir la réactivité de l'IHM
     */
    fun onClickCoucou(view: View)
    {
        //Affichage d'un Toast
        Toast.makeText(this,"Coucou", Toast.LENGTH_LONG).show();
    }

    /**
     * Fonction contenant le traitement long.
     */
    fun onClickTraitementLong(view: View)
    {
        val traitementLong = MonTraitementLong()
        traitementLong.execute("Enfin !")
    }

    /**
     *
     * AsyncTask<A, B, C>
     *     A : Type de données passées au traitement long : fonction doInBackground()
     *     B : type des données envoyées depuis le traitement long au thread UI via onProgressUpdate
     *     C : Type de la donnée retournée par le traitement long : fonction doInBackground()
     */
    inner class MonTraitementLong() : AsyncTask<String, Int, String>() {
        /**
         * Fonction exécutée avant le traitement long sur le thread UI
         */
        override fun onPreExecute() {
            super.onPreExecute()
            Toast.makeText(this@MainActivity,"Début du traitement !!",Toast.LENGTH_LONG).show()
        }

        /**
         * Fonction exécutant le traitement long sur un nouveau thread
         */
        override fun doInBackground(vararg params: String?): String {
            //Execute 60 fois
            for (i in 1..20)
            {
                //Fait dormir le thread contenant le traitement long
                Thread.sleep(1000)
                //Appel de la fonction onProgressUpdate() sur le Thread UI.
                publishProgress(i)
            }
            return "Fin du traitement, message : ${params[0]}"
        }

        /*
         * Fonction exécutée pendant le traitement long sur le thread UI
         * Cette fonction est appelée à l'appel de la fonction publishProgress()
         */
        override fun onProgressUpdate(vararg values: Int?) {
            // * pour le spread operator
            super.onProgressUpdate(*values)
            pb_evolution.progress = values[0]!!.toInt()
        }

        /**
         * Fonction exécutée aprés le traitement long sur le thread UI
         */
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(this@MainActivity,"$result",Toast.LENGTH_LONG).show()
        }
    }
}
