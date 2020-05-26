# xhtml5-validator

This performs the following validations:

- [ ] xhtml schema validation using [validator/validator](https://github.com/validator/validator) (just disabled for now)
- [x] unique id attributes
- [x] internal links point to an existing element


# How to Use

Mount a volume and specify the path to the XHTML file as the argument.

```bash
# Clone this repo and run:
docker run --volume $(pwd):/data --rm -it $(docker build -q .) /data/test/fail-duplicate.xhtml
docker run --volume $(pwd):/data --rm -it $(docker build -q .) /data/test/fail-no-link-target.xhtml
docker run --volume $(pwd):/data --rm -it $(docker build -q .) /data/test/pass.xhtml
```