import org.scalatestplus.play.FakeApplicationFactory
import play.api.Application
import play.api.ApplicationLoader
import play.api.Environment

trait TraceApplicationFactory extends FakeApplicationFactory {

  private class TraceApplicationBuilder {
    def build(): Application = {
      val env = Environment.simple()
      val context = ApplicationLoader.Context.create(env)
      val loader = new TraceApplicationLoader()
      loader.load(context)
    }
  }

  def fakeApplication(): Application = new TraceApplicationBuilder().build()

}
