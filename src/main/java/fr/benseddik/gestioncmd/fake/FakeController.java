package fr.benseddik.gestioncmd.fake;

import org.springframework.web.bind.annotation.*;

@RestController
public class FakeController {

    @GetMapping("/fake")
    public String fake() {
        return "fake";
    }

    @GetMapping("/fake/param")
    public String fakeParam(@RequestParam String name) {
        return "fakeParam";
    }

    @GetMapping("/fake/{condition}")
    public String fakeCondition(@PathVariable boolean condition) {
        return condition?"YES":"NO";
    }

    @PostMapping("/fake")
    public FakeObject fakePost(@RequestBody FakeObject body) {
        return body;
    }
}
