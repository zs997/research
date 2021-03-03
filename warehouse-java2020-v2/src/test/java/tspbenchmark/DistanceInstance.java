package tspbenchmark;

import org.python.antlr.ast.Str;

public class DistanceInstance {
    int cityNum;
    double distanceMatrix[][];
    public int getCityNum() {
        return cityNum;
    }
    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }
    private DistanceInstance(String data, int cityNum){
       this.cityNum = cityNum;
       String[] split = data.split("\\s+");
       this.distanceMatrix = new double[cityNum][cityNum];
       int colNum = 1;
       int count = 0;
       for (int i = 0; i < this.distanceMatrix.length; i++) {
           for (int j = 0;j < colNum;j++){
               this.distanceMatrix[i][j] = Double.valueOf(split[count].trim());
               this.distanceMatrix[j][i] = this.distanceMatrix[i][j];
               count++;
           }
           colNum++;
       }
   }
   public static DistanceInstance getInstance48(){
        String s = "0 593 0 409 258 0 566 331 171 0" +
                " 633 586 723 874 0 257 602 522 679 390" +
                " 0 91 509 325 482 598 228 0 412 627" +
                " 506 663 227 169 383 0 378 755 634 791" +
                " 397 175 349 167 0 593 416 564 721 271" +
                " 445 509 293 429 0 150 598 414 571 488" +
                " 112 120 267 233 541 0 659 488 630 787" +
                " 205 511 575 304 470 76 607 0 80 566" +
                " 382 539 572 196 77 351 317 563 63 629" +
                " 0 434 893 699 856 524 231 405 303 138" +
                " 595 289 606 373 0 455 417 433 590 313" +
                " 304 371 228 394 158 399 224 425 530 0" +
                " 134 583 399 566 530 154 105 309 275 575" +
                " 34 638 29 298 434 0 649 945 824 981" +
                " 446 423 620 357 280 649 504 648 588 416" +
                " 584 546 0 259 364 180 337 555 272 175" +
                " 338 466 403 264 469 232 549 265 249 656" +
                " 0 505 354 110 70 819 618 421 602 730" +
                " 660 509 728 478 795 529 494 920 276 0" +
                " 710 117 375 354 679 693 626 720 848 533" +
                " 715 610 683 986 534 700 1038 481 345 0" +
                " 488 784 663 820 289 262 459 196 119 488" +
                " 343 502 427 255 423 385 161 495 759 877" +
                " 0 353 641 520 677 282 110 324 61 125" +
                " 353 208 364 292 261 288 250 315 352 616" +
                " 734 154 0 324 275 91 248 638 437 240" +
                " 421 549 486 329 552 297 614 348 314 739" +
                " 95 187 392 578 435 0 605 287 431 588" +
                " 313 445 520 470 598 143 610 215 577 734" +
                " 144 595 788 352 527 404 627 484 385 0" +
                " 372 229 39 196 686 485 288 469 597 511" +
                " 397 578 345 662 396 361 787 143 135 346" +
                " 626 483 54 377 0 330 484 361 518 378" +
                " 119 260 150 278 323 174 389 276 414 185" +
                " 207 468 193 475 577 307 164 276 326 324" +
                " 0 581 877 756 913 370 355 552 289 212" +
                " 581 436 571 520 348 516 478 84 588 852" +
                " 970 93 247 671 720 719 400 0 154 460" +
                " 276 433 612 298 63 453 419 460 190 526" +
                " 158 475 322 175 690 126 372 577 529 396" +
                " 191 471 239 250 622 0 70 523 339 496" +
                " 569 191 27 346 312 516 83 589 47 368" +
                " 385 68 583 189 435 640 422 287 254 534" +
                " 302 249 515 115 0 606 183 216 147 715" +
                " 719 522 703 831 549 611 615 579 896 546" +
                " 596 1021 377 139 209 860 717 288 416 242" +
                " 558 953 473 536 0 585 427 563 720 179" +
                " 437 501 196 362 80 532 108 558 498 163" +
                " 567 552 395 659 544 391 256 478 154 526" +
                " 318 484 452 515 556 0 544 840 719 876" +
                " 311 318 515 252 175 508 399 494 483 311" +
                " 479 441 154 551 815 933 65 210 634 683" +
                " 682 363 77 585 479 916 399 0 496 525" +
                " 595 751 147 253 468 85 251 208 351 236" +
                " 435 387 162 393 441 427 691 646 280 145" +
                " 509 249 558 239 373 538 430 654 128 336" +
                " 0 317 289 105 262 631 430 233 414 542" +
                " 479 332 545 290 607 341 307 732 88 201" +
                " 406 571 428 21 407 68 269 664 184 247" +
                " 302 471 627 503 0 648 68 316 362 584" +
                " 598 564 625 753 418 653 484 621 891 415" +
                " 638 943 395 412 95 782 639 333 285 287" +
                " 482 875 515 578 209 425 838 523 347 0" +
                " 211 660 476 633 466 74 182 243 171 489" +
                " 66 555 150 227 351 108 432 326 572 777" +
                " 271 184 391 492 439 166 364 252 145 673" +
                " 438 327 327 384 715 0 475 137 295 452" +
                " 437 428 391 452 580 271 480 337 448 718" +
                " 268 465 770 222 391 254 609 466 255 138" +
                " 241 309 702 342 405 287 278 665 376 277" +
                " 167 542 0 654 151 319 266 755 767 570" +
                " 751 879 561 659 627 627 944 558 644 1069" +
                " 425 262 103 908 765 336 428 290 606 1001" +
                " 521 584 122 568 964 666 350 169 721 299" +
                " 0 710 239 487 546 616 660 626 687 815" +
                " 443 715 509 683 953 440 700 1005 457 583" +
                " 279 844 701 490 310 458 544 937 577 640" +
                " 393 450 900 548 512 179 777 229 353 0" +
                " 585 135 385 458 499 535 501 562 690 333" +
                " 590 399 558 828 330 575 880 332 481 215" +
                " 719 576 365 200 356 419 812 452 515 318" +
                " 340 775 438 387 120 652 104 289 121 0" +
                " 246 373 183 340 745 472 237 528 656 593" +
                " 364 659 332 649 455 349 846 202 279 490" +
                " 685 542 157 525 144 383 778 174 289 386" +
                " 585 741 618 132 431 426 395 434 630 505" +
                " 0 788 208 456 488 724 738 704 765 893" +
                " 558 793 624 761 1031 555 778 1083 535 552" +
                " 188 922 779 473 425 427 622 1015 655 718" +
                " 343 565 978 663 487 138 855 307 284 138" +
                " 235 571 0 446 162 111 268 624 559 362" +
                " 543 671 458 451 524 419 736 455 436 861" +
                " 217 207 279 700 557 128 325 82 398 793" +
                " 313 376 175 465 756 563 142 220 513 187" +
                " 223 391 289 226 360 0 166 437 247 404" +
                " 749 435 150 590 556 597 402 663 295 612" +
                " 459 387 827 189 343 554 666 531 221 589" +
                " 208 372 759 137 177 450 589 722 675 196" +
                " 495 389 459 498 694 569 80 635 290 0" +
                " 523 81 188 255 596 636 439 620 648 430" +
                " 528 496 496 813 427 513 938 294 284 193" +
                " 777 634 205 297 159 475 870 390 453 119" +
                " 437 833 535 219 139 590 168 131 310 208" +
                " 303 279 92 367 0 235 371 187 344 581" +
                " 348 151 364 469 429 240 495 208 525 291" +
                " 225 682 32 283 488 521 378 103 384 150" +
                " 219 614 94 165 384 421 577 454 92 429" +
                " 302 254 432 489 364 165 569 224 154 301" +
                " 0 369 205 289 446 537 328 286 355 483" +
                " 371 375 437 343 554 269 360 673 116 385" +
                " 322 512 369 149 238 230 209 605 237 300" +
                " 352 378 568 445 172 281 436 108 332 343" +
                " 218 290 421 164 354 201 149 0 121 570" +
                " 386 543 518 142 84 297 263 570 35 636" +
                " 29 319 432 36 534 236 482 687 373 238" +
                " 301 581 349 222 466 162 55 583 562 429" +
                " 381 294 625 96 452 631 687 562 336 765" +
                " 423 299 500 212 347 0";
      return  new DistanceInstance(s,48);
   }
   private DistanceInstance(){

   }

   public static DistanceInstance getInstance24(){
       String s = "0 257 0 187 196 0 91 228 158 0 150 112" +
               " 96 120 0 80 196 88 77 63 0 130 167 59" +
               " 101 56 25 0 134 154 63 105 34 29 22 0" +
               " 243 209 286 159 190 216 229 225 0 185 86 124" +
               " 156 40 124 95 82 207 0 214 223 49 185 123" +
               " 115 86 90 313 151 0 70 191 121 27 83 47" +
               " 64 68 173 119 148 0 272 180 315 188 193 245" +
               " 258 228 29 159 342 209 0 219 83 172 149 79" +
               " 139 134 112 126 62 199 153 97 0 293 50 232" +
               " 264 148 232 203 190 248 122 259 227 219 134 0" +
               " 54 219 92 82 119 31 43 58 238 147 84 53" +
               " 267 170 255 0 211 74 81 182 105 150 121 108" +
               " 310 37 160 145 196 99 125 173 0 290 139 98" +
               " 261 144 176 164 136 389 116 147 224 275 178 154" +
               " 190 79 0 268 53 138 239 123 207 178 165 367" +
               " 86 187 202 227 130 68 230 57 86 0 261 43" +
               " 200 232 98 200 171 131 166 90 227 195 137 69" +
               " 82 223 90 176 90 0 175 128 76 146 32 76" +
               " 47 30 222 56 103 109 225 104 164 99 57 112" +
               " 114 134 0 250 99 89 221 105 189 160 147 349" +
               " 76 138 184 235 138 114 212 39 40 46 136 96" +
               " 0 192 228 235 108 119 165 178 154 71 136 262" +
               " 110 74 96 264 187 182 261 239 165 151 221 0" +
               " 121 142 99 84 35 29 42 36 220 70 126 55" +
               " 249 104 178 60 96 175 153 146 47 135 169 0";
      return new DistanceInstance(s,24);
   }

   public static DistanceInstance getInstance6(){
       String s = "0 257 0 187 196 0 91 228 158 0 150 112 96 120 0 80 196 88 77 63 0";
       DistanceInstance distanceInstance = new DistanceInstance(s, 6);
       double[][] distanceMatrix = distanceInstance.getDistanceMatrix();
       for (int i = 0; i < distanceMatrix.length; i++) {
           for (int j = 0; j < distanceMatrix[i].length; j++) {
               System.out.print(distanceMatrix[i][j]+",");
           }
           System.out.println();
       }
       return distanceInstance;
   }
}
