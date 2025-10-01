package com.galafis.bigdata

import com.galafis.bigdata.config.AppConfig
import com.galafis.bigdata.core.SparkSessionManager
import org.apache.spark.sql.SparkSession
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MainSpec extends AnyFunSuite with Matchers with BeforeAndAfterAll {

  var spark: SparkSession = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    // Inicializa uma SparkSession local para testes
    spark = SparkSessionManager.getSparkSession("test-app", "local[*]")
  }

  override def afterAll(): Unit = {
    if (spark != null) {
      spark.stop()
    }
    super.afterAll()
  }

  test("Main.main should run without errors for batch mode") {
    // Redireciona System.out para capturar a saída, se necessário
    // val outContent = new java.io.ByteArrayOutputStream()
    // Console.withOut(outContent) {
    //   Main.main(Array("batch"))
    // }
    // outContent.toString should include ("Batch processing completed")
    noException should be thrownBy Main.main(Array("batch"))
  }

  test("Main.main should run without errors for streaming mode") {
    noException should be thrownBy Main.main(Array("streaming"))
  }

  test("Main.main should run without errors for analytics mode") {
    noException should be thrownBy Main.main(Array("analytics"))
  }

  test("Main.main should exit with error for invalid mode") {
    // Captura a saída de erro padrão para verificar a mensagem
    val errContent = new java.io.ByteArrayOutputStream()
    Console.withErr(errContent) {
      // System.exit é chamado, mas o teste continua
      // Apenas verificamos se a mensagem de erro esperada é impressa
      noException should be thrownBy Main.main(Array("invalid"))
    }
    errContent.toString should include ("Modo de execução inválido")
  }

  test("AppConfig should load configuration correctly") {
    val config = AppConfig.load()
    config.sparkConfig.appName shouldBe "scala-spark-big-data"
    config.sparkConfig.master shouldBe "local[*]"
    config.dataConfig.inputPath shouldBe "data/input"
    config.dataConfig.outputPath shouldBe "data/output"
  }

  test("SparkSessionManager should provide a SparkSession") {
    val testSpark = SparkSessionManager.getSparkSession("test-session", "local[*]")
    testSpark should not be null
    testSpark.sparkContext.appName shouldBe "test-session"
    testSpark.stop()
  }

}

