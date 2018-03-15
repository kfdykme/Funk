package kfdykme.funk.util

import android.os.Environment
import android.util.Log
import java.io.*


/**
 * Created by wimkf on 2018/3/9.
 */

class FileUtils{


    companion object {
        val baseDir = "/Funk/"


        private val FileNotFoundException: Throwable? = null



    @Throws(IOException::class)
    fun createFile(reDir: String, name: String, content: String) {

        val root = Environment.getExternalStorageDirectory()

        val reFile = File(root.getCanonicalPath() + baseDir + reDir)

        if (!reFile.exists())

            reFile.mkdirs()

        val tFile = File(reFile.getCanonicalPath() + "/" + name)

        if (tFile.exists())

            tFile.delete()

        val raf = RandomAccessFile(tFile, "rw")

        raf.write(content.toByteArray())

        raf.close()

        Log.i("FileUtils", "create or edit file : $name/$content")

    }

        @Throws(IOException::class, Throwable::class)
        fun readFile(reDir: String, name: String, fileType: String): String {

            val root = Environment.getExternalStorageDirectory()


            val dir = File(root.canonicalPath + baseDir + reDir)

            if (!dir.exists()) {

                dir.mkdirs()

            }

            val targetFile = File(dir.canonicalPath + "/" + name + "." + fileType)


            var content = ""

            if (targetFile.isFile && targetFile.exists()) {


                var read: InputStreamReader? = null



                read = InputStreamReader(FileInputStream(targetFile), "utf8")


                val bufferedReader = BufferedReader(read)

                var line: String? = null


                line = bufferedReader.readLine()

                while (line != null) {
                    content += line
                    line = bufferedReader.readLine()
                }


            } else {


                throw this?.FileNotFoundException!!

            }

            return content

        }

    }
}
