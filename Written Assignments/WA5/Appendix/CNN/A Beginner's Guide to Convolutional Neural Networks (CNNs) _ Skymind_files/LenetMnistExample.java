if ( 'prettyPrint' in window ) {} else {
    document.write( '<script type="text/javascript" src="https://gist-it.appspot.com/assets/prettify/prettify.js"></script>' );
}


document.write( '<link rel="stylesheet" href="https://gist-it.appspot.com/assets/embed.css"/>' );


document.write( '<link rel="stylesheet" href="https://gist-it.appspot.com/assets/prettify/prettify.css"/>' );

document.write( '<div class="gist-it-gist">\n<div class="gist-file">\n    <div class="gist-data">\n        \n        <pre class="prettyprint">            Create an iterator using the batch size for one iteration\n         */\n        log.info("Load data....");\n        DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize,true,12345);\n        DataSetIterator mnistTest = new MnistDataSetIterator(batchSize,false,12345);\n\n        /*\n            Construct the neural network\n         */\n        log.info("Build model....");\n\n        // learning rate schedule in the form of &lt;Iteration #, Learning Rate&gt;\n        Map&lt;Integer, Double&gt; lrSchedule = new HashMap&lt;&gt;();\n        lrSchedule.put(0, 0.01);\n        lrSchedule.put(1000, 0.005);\n        lrSchedule.put(3000, 0.001);\n\n        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()\n                .seed(seed)\n                .l2(0.0005)\n                .weightInit(WeightInit.XAVIER)\n                .updater(new Nesterovs(0.01, 0.9))\n                .list()\n                .layer(0, new ConvolutionLayer.Builder(5, 5)\n                        //nIn and nOut specify depth. nIn here is the nChannels and nOut is the number of filters to be applied\n                        .nIn(nChannels)\n                        .stride(1, 1)\n                        .nOut(20)\n                        .activation(Activation.IDENTITY)\n                        .build())\n                .layer(1, new SubsamplingLayer.Builder(PoolingType.MAX)\n                        .kernelSize(2,2)\n                        .stride(2,2)\n                        .build())\n                .layer(2, new ConvolutionLayer.Builder(5, 5)\n                        //Note that nIn need not be specified in later layers\n                        .stride(1, 1)\n                        .nOut(50)\n                        .activation(Activation.IDENTITY)\n                        .build())\n                .layer(3, new SubsamplingLayer.Builder(PoolingType.MAX)\n                        .kernelSize(2,2)\n                        .stride(2,2)\n                        .build())\n                .layer(4, new DenseLayer.Builder().activation(Activation.RELU)\n                        .nOut(500).build())\n                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)\n                        .nOut(outputNum)\n                        .activation(Activation.SOFTMAX)\n                        .build())\n                .setInputType(InputType.convolutionalFlat(28,28,1)) //See note below\n                .backprop(true).pretrain(false).build();\n\n        /*\n        Regarding the .setInputType(InputType.convolutionalFlat(28,28,1)) line: This does a few things.\n        (a) It adds preprocessors, which handle things like the transition between the convolutional/subsampling layers\n            and the dense layer\n        (b) Does some additional configuration validation\n        (c) Where necessary, sets the nIn (number of input neurons, or input depth in the case of CNNs) values for each\n            layer based on the size of the previous layer (but it won\'t override values manually set by the user)\n\n        InputTypes can be used with other layer types too (RNNs, MLPs etc) not just CNNs.\n        For normal images (when using ImageRecordReader) use InputType.convolutional(height,width,depth).\n        MNIST record reader is a special case, that outputs 28x28 pixel grayscale (nChannels=1) images, in a "flattened"\n        row vector format (i.e., 1x784 vectors), hence the "convolutionalFlat" input type used here.\n        */\n\n        MultiLayerNetwork model = new MultiLayerNetwork(conf);\n        model.init();\n\n\n        log.info("Train model....");\n        model.setListeners(new ScoreIterationListener(10)); //Print score every 10 iterations\n        for( int i=0; i&lt;nEpochs; i++ ) {\n            model.fit(mnistTrain);\n            log.info("*** Completed epoch {} ***", i);\n\n            log.info("Evaluate model....");\n            Evaluation eval = model.evaluate(mnistTest);\n            log.info(eval.stats());\n            mnistTest.reset();\n        }\n        log.info("****************Example finished********************");\n    }</pre>\n        \n    </div>\n    \n    <div class="gist-meta">\n        \n        <span><a href="https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/convolution/LenetMnistExample.java">This Gist</a> brought to you by <a href="https://gist-it.appspot.com">gist-it</a>.</span>\n        \n        <span style="float: right; color: #369;"><a href="https://github.com/deeplearning4j/dl4j-examples/raw/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/convolution/LenetMnistExample.java">view raw</a></span>\n        <span style="float: right; margin-right: 8px;">\n            <a style="color: rgb(102, 102, 102);" href="https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/convolution/LenetMnistExample.java">dl4j-examples/src/main/java/org/deeplearning4j/examples/convolution/LenetMnistExample.java</a></span>\n            <!-- Generated by: https://gist-it.appspot.com -->\n    </div>\n    \n</div>\n</div>' );

document.write( '<script type="text/javascript">prettyPrint();</script>' );